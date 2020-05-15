package data_analyzer.models

import data_analyzer.models.SupportedDataType.SupportedDataType
import org.apache.spark.sql.Row
import org.apache.spark.sql.catalyst.encoders
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types._

import scala.collection.immutable.HashMap

case class Schema(private val schemaDefinition: Seq[(String, SupportedDataType)]) {
  private val TYPE_MAP = HashMap[SupportedDataType, DataType](
    SupportedDataType.INTEGER -> IntegerType,
    SupportedDataType.STRING -> StringType,
    SupportedDataType.FLOAT -> FloatType,
    SupportedDataType.DOUBLE -> DoubleType
  )

  def this(schemaDefinition: Map[Int, (String, SupportedDataType)]) = this(
    schemaDefinition.toSeq
      .sortBy(_._1)
      .map(_._2)
  )

  def getRowEncoder: encoders.ExpressionEncoder[Row] = {
    val fields = getDefinition
      .map { x => StructField(x._1, TYPE_MAP(x._2), nullable = false) }

    RowEncoder(StructType(fields))
  }


  def getDefinition: Seq[(String, SupportedDataType)] = schemaDefinition

  def getColumns: Seq[String] = schemaDefinition.map(x => x._1)
}

