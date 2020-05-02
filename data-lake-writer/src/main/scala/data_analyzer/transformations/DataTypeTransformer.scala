package data_analyzer.transformations
import java.time.Instant
import java.time.format.DateTimeFormatter

import data_analyzer.deserializers.DataRow.JSONArrayDataRow
import data_analyzer.deserializers.KafkaMessageDeserializer
import data_analyzer.models.{Schema, SupportedDataType}
import org.apache.spark.sql.{DataFrame, Dataset}


class DataTypeTransformer(dataFrame: DataFrame, schema: Schema){
  def transformToCorrectDataType(): DataFrame = {

    dataFrame
//      .select("payload")
//      .transform(KafkaMessageDeserializer.deserializeMessage(schema))
  }

}
