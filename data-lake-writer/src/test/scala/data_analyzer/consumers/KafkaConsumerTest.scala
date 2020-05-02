package data_analyzer.consumers

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import data_analyzer.{KafkaMessageMother, SparkTestSession}
import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.matchers.should.Matchers

class KafkaConsumerTest extends org.scalatest.FunSuite with EmbeddedKafka with KafkaMessageMother with Matchers with DatasetComparer with SparkTestSession {

  import spark.implicits._

  test("should consume data from Kafka and create a dataframe with two columns, key and raw_payload") {
    val userDefinedConfig = EmbeddedKafkaConfig(kafkaPort = 50092, zooKeeperPort = 0)

    withRunningKafkaOnFoundPort(userDefinedConfig) { implicit actualConfig =>

      createCustomTopic("topic")
      val consumer = new KafkaConsumer(spark, "localhost:50092", "topic")
      val keyColumnName = "key"
      val valueColumnName = "raw_payload"
      val frame = consumer.readKafkaStream(keyColumnName, valueColumnName)

      val schema = StructType(
        StructField(keyColumnName, StringType, nullable = false) :: StructField(valueColumnName, StringType, nullable = false) :: Nil)

      var df = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], schema)

      val query = frame.writeStream
        .foreachBatch { (output: DataFrame, batchId: Long) => df = df.union(output) }
        .start()

      query.awaitTermination(5000)
      Thread.sleep(1000)

      publishStringMessageToKafka("topic", getMessage("topic", "123", """[1, "Neel", "100"]"""))
      publishStringMessageToKafka("topic", getMessage("topic", "123", """[2, "Neel1", "10"]"""))
      publishStringMessageToKafka("topic", getMessage("topic", "123", """[3, "Neel2", "1"]"""))
      publishStringMessageToKafka("topic", getMessage("topic", "123", """[4, "Neel3", "1000"]"""))

      Thread.sleep(4000)


      val expected = Seq[(String, String)](
        (null, getMessage("topic", "123", """[1, "Neel", "100"]""")),
        (null, getMessage("topic", "123", """[2, "Neel1", "10"]""")),
        (null, getMessage("topic", "123", """[3, "Neel2", "1"]""")),
        (null, getMessage("topic", "123", """[4, "Neel3", "1000"]"""))
      ).toDF(keyColumnName, valueColumnName)


      assertSmallDatasetEquality(df, expected)


    }


  }
}
