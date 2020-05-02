package data_analyzer.models

import data_analyzer.models.SupportedDataType.SupportedDataType
import org.scalatest.Matchers

import scala.collection.immutable.HashMap


class SchemaTest extends org.scalatest.FunSuite with Matchers {

  test("should return correct schema as sequence") {
    val dataTypeMap = HashMap[Int, (String, SupportedDataType)](
      2 -> ("Roll", SupportedDataType.INTEGER),
      1 -> ("Name", SupportedDataType.STRING),
      3 -> ("Age", SupportedDataType.INTEGER)
    )
    val schema = new Schema(dataTypeMap)
    val definition = schema.getDefinition
    val expectedDefinition = Seq(
      ("Name", SupportedDataType.STRING),
      ("Roll", SupportedDataType.INTEGER),
      ("Age", SupportedDataType.INTEGER)
    )
    definition should contain inOrderElementsOf expectedDefinition
  }

}
