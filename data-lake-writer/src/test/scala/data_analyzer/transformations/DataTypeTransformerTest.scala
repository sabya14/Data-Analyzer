//package data_analyzer.transformations
//
//import java.util
//
//import com.github.mrpowers.spark.fast.tests.DatasetComparer
//import data_analyzer.SparkTestSession
//import data_analyzer.models.{JSONArrayDataRow, Schema, SupportedDataType}
//import net.manub.embeddedkafka.{EmbeddedKafka, EmbeddedKafkaConfig}
//import org.scalatest.Matchers
//
//class DataTypeTransformerTest extends org.scalatest.FunSuite with SparkTestSession with DatasetComparer with Matchers{
//
//
//  test("get dataframe with correct types given a schema") {
//    import spark.implicits._
//
//    val schema = new Schema(Seq(
//      ("Roll", SupportedDataType.INTEGER),
//      ("Age", SupportedDataType.INTEGER),
//      ("Name", SupportedDataType.STRING)
//    ))
//
//    val df = Seq(
//      (new JSONArrayDataRow(util.Arrays.asList("1", "12", "Neel")), 1),
//      (new JSONArrayDataRow(util.Arrays.asList("2", "12", "Palash")), 2),
//      (new JSONArrayDataRow(util.Arrays.asList("3", "12", "Someone")), 3)
//    ).toDF("payload")
//
//    val expectedDf = Seq(
//      (1, 12, "Neel"),
//      (2, 12, "Palash"),
//      (3, 12, "Someone"),
//    ).toDF("Roll", "Age", "Name")
//
//    val transformer = new DataTypeTransformer(df, schema)
//    val outputDf = transformer.transformToCorrectDataType()
//    assertSmallDatasetEquality(expectedDf, outputDf)
//  }
//}
