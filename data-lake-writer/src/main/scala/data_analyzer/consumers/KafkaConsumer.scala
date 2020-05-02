package data_analyzer.consumers

import org.apache.spark.sql.{DataFrame, SparkSession}

class KafkaConsumer(spark: SparkSession, kafkaBrokers: String, topic: String) {
  def readKafkaStream(keyColumnName: String, valueColumnName: String): DataFrame = {
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaBrokers)
      .option("subscribe", topic)
      .option("startingOffsets", "latest")
      .load()
      .selectExpr(s"CAST(key AS STRING) as ${keyColumnName}", s"CAST(value AS STRING) as ${valueColumnName}")
  df
  }
}
