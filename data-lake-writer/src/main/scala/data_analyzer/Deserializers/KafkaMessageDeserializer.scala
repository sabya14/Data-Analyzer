package data_analyzer.Deserializers

import data_analyzer.Deserializers.DataRow.JSONArrayDataRow

import scala.collection.immutable.{HashMap, SortedMap}
import scala.collection.mutable

object KafkaMessageDeserializer {

  def deserializeMessage(schema: mutable.HashMap[Int, (String, String)], message: JSONArrayDataRow): mutable.HashMap[String, Any] = {
    var toInteger = (_: String).toInt
    var toString = (_: String).toString

    val castMap = HashMap(
      "Integer" -> toInteger,
      "String" -> toString
    )

    val tokens = message.getValues
    val sortedSchema = SortedMap.empty[Int, (String, String)] ++ schema
    val results = mutable.HashMap.empty[String, Any]
    sortedSchema.foreach {
      case (key, value) => {
        val valueToCast = tokens.get(key)
        val castingFunction = castMap(value._2)
        results(value._1) = castingFunction(valueToCast)
      }
    }
    results
  }
}
