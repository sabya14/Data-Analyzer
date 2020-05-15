package data_analyzer

import java.util.Properties

import data_analyzer.consumers.KafkaConsumer
import data_analyzer.models.Schema
import data_analyzer.models.SupportedDataType.SupportedDataType
import data_analyzer.transformations.RawPayloadTransformer
import data_analyzer.writers.HadoopWriter
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.StreamingQuery

import scala.collection.immutable.HashMap


class WriterJob(topicName: String, schemaInfo: HashMap[Int, (String, SupportedDataType)]) {

  var properties: Properties = new Properties()
  properties.load(getClass.getClassLoader.getResource("config.properties").openStream())


  def start(): StreamingQuery = {
    val spark = SparkSession.builder
      .appName(properties.getProperty("appNamePrefix"))
      .getOrCreate()

    val schema = new Schema(schemaInfo)
    val consumer = new KafkaConsumer(spark, properties.getProperty("kafka.brokers"), topicName)
    val streamingDF = consumer.readKafkaStream(properties.getProperty("kafka.consumers.keyName"), properties.getProperty("kafka.consumers.valueName"))
    val transformer = new RawPayloadTransformer()
    val transformedStreamingDf = streamingDF
      .transform(transformer.transformKafkaMessage(spark, _))
      .transform(transformer.deserializePayload(spark, _))
      .transform(transformer.transformToCorrectSchema(_, schema))

    val writer = new HadoopWriter(properties.getProperty("hadoop.uri"))
    writer.writeToHadoop(transformedStreamingDf, dir = topicName, checkpointDir = topicName + "_checkpoint")
  }

}