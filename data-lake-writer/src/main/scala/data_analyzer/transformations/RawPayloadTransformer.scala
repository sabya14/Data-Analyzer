package data_analyzer.transformations

import data_analyzer.deserializers.KafkaMessageDeserializer
import data_analyzer.models.{JSONArrayDataRow, KafkaMessage, Schema}
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.catalyst.encoders.{ExpressionEncoder, RowEncoder}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

class RawPayloadTransformer {
  def deserializePayload(spark: SparkSession, inputDF: Dataset[KafkaMessage]): Dataset[JSONArrayDataRow] = {
    import spark.implicits._
    inputDF.select($"payload" as "row")
      .as[JSONArrayDataRow]
  }

  def transformKafkaMessage(spark: SparkSession, inputDf: DataFrame): Dataset[KafkaMessage] = {
    import spark.implicits._
    inputDf.select(from_json($"raw_payload", ScalaReflection.schemaFor[KafkaMessage].dataType).as("json"))
      .select("json.*")
      .as[KafkaMessage]
  }

  def transformToCorrectSchema(inputDf: Dataset[JSONArrayDataRow], schema: Schema): DataFrame = {
    val schema1 = StructType(Seq(
      StructField("ID", IntegerType, nullable = false),
      StructField("Name", StringType),
      StructField("Marks", IntegerType, nullable = false)
    ))
    val encoder: ExpressionEncoder[Row] = RowEncoder(schema1)

    inputDf.map {
      x =>
        val map1 = KafkaMessageDeserializer.deserializeMessage(schema, x)
        val valuesInOrder = schema.getColumns.map(cName => map1.get(cName).get)
        val row = Row(valuesInOrder: _*)
        row
    }(encoder)

  }

}
