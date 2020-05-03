package data_analyzer.deserializers

import data_analyzer.models.SupportedDataType.SupportedDataType
import data_analyzer.models.{JSONArrayDataRow, Schema, SupportedDataType}

import scala.collection.immutable.HashMap
import scala.collection.mutable

object KafkaMessageDeserializer {
  val castMap = HashMap[SupportedDataType, Function1[String, Any]](
    SupportedDataType.INTEGER -> (_.toInt),
    SupportedDataType.STRING -> identity,
    SupportedDataType.FLOAT -> (_.toFloat),
    SupportedDataType.DOUBLE -> (_.toDouble)
  )

  def deserializeMessage(schema: Schema, message: JSONArrayDataRow): mutable.HashMap[String, Any] = {
    val tokens = message.row
    val results = mutable.HashMap.empty[String, Any]
    val definition = schema.getDefinition
    for (index <- definition.indices) {
      val (columnName, dataType) = definition(index)
      val valueToCast = tokens(index)
      val castingFunction = castMap(dataType)
      results(columnName) = castingFunction(valueToCast)
    }
    results
  }
}

