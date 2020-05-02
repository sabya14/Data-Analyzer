package data_analyzer.deserializers

import data_analyzer.deserializers.DataRow.JSONArrayDataRow
import data_analyzer.models.{Schema, SupportedDataType}

import scala.collection.mutable

class KafkaMessageDeserializerTest extends org.scalatest.FunSuite {
  test("should deserialize a kafka message in the form of JSONArray") {
    val jsonArray = "[\"\\\"neel,s\\\"\", \"12\"]";
    val row = JSONArrayDataRow.fromSerializedJsonArray(jsonArray)
    val schema = new Schema(Seq(("name", SupportedDataType.STRING), ("roll", SupportedDataType.INTEGER)))
    val expectedHashMap = mutable.HashMap("name" -> "\"neel,s\"", "roll" -> 12)
    val hashMap = KafkaMessageDeserializer.deserializeMessage(schema, row)
    assert(expectedHashMap.equals(hashMap))
  }
}
