package data_analyzer.models

object SupportedDataType extends Enumeration {
  type SupportedDataType = Value
  val INTEGER = Value("Integer")
  val DOUBLE = Value("double")
  val FLOAT = Value("float")
  val STRING = Value("string")
}
