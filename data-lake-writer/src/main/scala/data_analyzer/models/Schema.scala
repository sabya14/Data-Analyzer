package data_analyzer.models

import data_analyzer.models.SupportedDataType.SupportedDataType


case class Schema(private val schemaDefinition: Seq[(String, SupportedDataType)]) {

  def this(schemaDefinition: Map[Int, (String, SupportedDataType)]) = this(
    schemaDefinition.toSeq
      .sortBy(_._1)
      .map(_._2)
  )

  def getDefinition: Seq[(String, SupportedDataType)] = schemaDefinition
  def getColumns: Seq[String] = schemaDefinition.map(x => x._1)
}

