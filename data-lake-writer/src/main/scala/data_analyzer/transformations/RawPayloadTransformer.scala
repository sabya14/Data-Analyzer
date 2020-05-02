package data_analyzer.transformations

import data_analyzer.models.DataRow.JSONArrayDataRow
import data_analyzer.models.KafkaMessage
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.{DataTypes, StructField}
import org.apache.spark.sql.{DataFrame, Dataset, Encoders, SparkSession}

import scala.util.parsing.json.JSON
import org.apache.spark.sql.functions._

class RawPayloadTransformer {
  def transformKafkaMessage(spark: SparkSession, inputDf: DataFrame): Dataset[KafkaMessage] = {
    import spark.implicits._
    inputDf.select(from_json($"raw_payload",  ScalaReflection.schemaFor[KafkaMessage].dataType).as("json"))
      .select("json.*")
      .as[KafkaMessage]
  }

}
