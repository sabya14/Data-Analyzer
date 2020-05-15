package data_analyzer

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import data_analyzer.models.SupportedDataType
import data_analyzer.models.SupportedDataType.SupportedDataType
import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.spark.sql.types.StructType
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers

import scala.collection.immutable.HashMap

class WriterJobTest extends org.scalatest.FunSuite
  with EmbeddedKafka
  with KafkaMessageMother
  with Matchers
  with DatasetComparer
  with SparkTestSession
  with HDFSTestWrapper
  with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    startHDFS()
  }

  override protected def afterAll(): Unit = {
    shutdownHDFS()
  }

  test("given correct schema info, and info about Kafka topic, it should read from Kafka and write to Hadoop") {
    import spark.implicits._
    val userDefinedConfig = EmbeddedKafkaConfig(kafkaPort = 50092, zooKeeperPort = 0)
    withRunningKafkaOnFoundPort(userDefinedConfig) { implicit actualConfig =>
      val topicName = "testTopic"
      val schemaInfo = HashMap[Int, (String, SupportedDataType)](
        1 -> ("Roll", SupportedDataType.INTEGER),
        2 -> ("Name", SupportedDataType.STRING),
        3 -> ("Age", SupportedDataType.INTEGER)
      )

      createCustomTopic(topicName)
      val job = new WriterJob(topicName, schemaInfo)
      publishStringMessageToKafka(topicName, getMessage(topicName, "1231", """[1, "Neel", "100"]"""))
      publishStringMessageToKafka(topicName, getMessage(topicName, "123", """[2, "Neel1", "10"]"""))
      publishStringMessageToKafka(topicName, getMessage(topicName, "123", """[3, "Neel2", "1"]"""))
      publishStringMessageToKafka(topicName, getMessage(topicName, "123", """[4, "Neel3", "1000"]"""))


      val query = job.start()
      query.awaitTermination(10000)

      val expectedSchemaOfFile = new StructType()
        .add("Roll", "integer")
        .add("Name", "string")
        .add("Age", "integer")

      val expectedHadoopDir = getNameNodeURI + s"/${topicName}"

      val expectedDataFrame = Seq(
        (1, "Neel", 100),
        (2, "Neel1", 10),
        (3, "Neel2", 1),
        (4, "Neel3", 1000)
      ).toDF("Roll", "Name", "Age")

      val frame = spark.read.
        option("sep", ",")
        .schema(expectedSchemaOfFile)
        .csv(expectedHadoopDir)

      assertSmallDatasetEquality(expectedDataFrame, frame, ignoreNullable = true)
    }
  }

  test("given, it should read from Kafka and write to Hadoop") {
    import spark.implicits._

    val topicName = "testTopic"
    val expectedSchemaOfFile = new StructType()
      .add("Roll", "integer")
      .add("Name", "string")
      .add("Age", "integer")

    val expectedHadoopDir = "hdfs://localhost:9000" + s"/${topicName}"

    val expectedDataFrame = Seq(
      (1, "Neel", 100),
      (2, "Neel1", 10),
      (3, "Neel2", 1),
      (4, "Neel3", 1000)
    ).toDF("Roll", "Name", "Age")

    val frame = spark.read.
      option("sep", ",")
      .schema(expectedSchemaOfFile)
      .csv(expectedHadoopDir)

    assertSmallDatasetEquality(expectedDataFrame, frame, ignoreNullable = true)

  }
}


