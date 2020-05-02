package data_analyzer.models


case class KafkaMessage(val metadata: MetaData, val payload: Seq[String]) {
}

