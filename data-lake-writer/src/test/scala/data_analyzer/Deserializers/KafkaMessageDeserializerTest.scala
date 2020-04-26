package data_analyzer.Deserializers

import data_analyzer.Deserializers.DataRow.JSONArrayDataRow

import scala.collection.immutable.{HashMap, SortedMap}
import scala.collection.mutable




class KafkaMessageDeserializerTest extends org.scalatest.FunSuite {
  test("should deserialize a kafka message in the form of JSONArray") {
    val jsonArray = "[\"\\\"neel,s\\\"\", \"12\"]";
    val row = new JSONArrayDataRow(jsonArray)
    val schema = mutable.HashMap(0 -> ("name", "String"), 1 -> ("roll", "Integer"))
    val expectedHashMap = mutable.HashMap("name" -> "\"neel,s\"", "roll" -> 12)
    val hashMap = KafkaMessageDeserializer.deserializeMessage(schema, row)
    assert(expectedHashMap.equals(hashMap))
  }
}
