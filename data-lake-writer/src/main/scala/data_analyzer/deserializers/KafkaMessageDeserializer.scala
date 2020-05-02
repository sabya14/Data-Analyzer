//package data_analyzer.deserializers
//
//import data_analyzer.models.SupportedDataType.SupportedDataType
//import data_analyzer.models.{DataRow, Schema, SupportedDataType}
//
//import scala.collection.immutable.HashMap
//import scala.collection.mutable
//
//object KafkaMessageDeserializer {
//  val castMap = HashMap[SupportedDataType, Function1[String, Any]](
//    SupportedDataType.INTEGER -> (_.toInt),
//    SupportedDataType.STRING -> identity,
//    SupportedDataType.FLOAT -> (_.toFloat),
//    SupportedDataType.DOUBLE -> (_.toDouble)
//  )
//
//  def deserializeMessage(schema: Schema, message: DataRow): mutable.HashMap[String, Any] = {
//    val tokens = message.getValues
//    val results = mutable.HashMap.empty[String, Any]
//    val definition = schema.getDefinition
//    for (index <- definition.indices) {
//      val (columnName, dataType) = definition(index)
//      val valueToCast = tokens.get(index)
//      val castingFunction = castMap(dataType)
//      results(columnName) = castingFunction(valueToCast)
//    }
//    results
//  }
//}
//
