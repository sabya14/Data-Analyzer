package data_analyzer.transformations

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import data_analyzer.models.{KafkaMessage, MetaData}
import data_analyzer.{KafkaMessageMother, SparkTestSession}
import org.scalatest.matchers.should.Matchers

class RawPayloadTransformerTest extends org.scalatest.FunSuite with SparkTestSession with KafkaMessageMother with DatasetComparer with Matchers{

  import spark.implicits._

  test("should be able to convert raw payload into a dataFrame with columns as metadata and dataRow") {

    val keyColumnName = "key"
    val valueColumnName = "raw_payload"

    val producerId = "producer"
    val eventKey = "123"
    val message1 = """["1", "Neel", "100"]"""
    val message2 = """["2", "Neel1", "10"]"""
    val message3 = """["3", "Neel2", "1"]"""
    val message4 = """["4", "Neel3", "1000"]"""
    val input = Seq[(String, String)](
      (null, getMessage(producerId, eventKey, message1)),
      (null, getMessage(producerId, eventKey, message2)),
      (null, getMessage(producerId, eventKey, message3)),
      (null, getMessage(producerId, eventKey, message4))
    ).toDF(keyColumnName, valueColumnName)

    val expectedDS = Seq(
      new KafkaMessage(new MetaData(producerId, message1.length, eventKey, 1524237281590L), Seq("1", "Neel", "100")),
      new KafkaMessage(new MetaData(producerId, message2.length, eventKey, 1524237281590L), Seq("2", "Neel1", "10")),
      new KafkaMessage(new MetaData(producerId, message3.length, eventKey, 1524237281590L), Seq("3", "Neel2", "1")),
      new KafkaMessage(new MetaData(producerId, message4.length, eventKey, 1524237281590L), Seq("4", "Neel3", "1000"))
    ).toDS()

    val transformer = new RawPayloadTransformer()
    val actual = transformer.transformKafkaMessage(spark, input)
    actual.collect().toList should contain allElementsOf expectedDS.collect().toList
  }
}
