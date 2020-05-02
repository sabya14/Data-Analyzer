package data_analyzer.models

case class MetaData(
                     val producer_id: String,
                     val size: Long,
                     val message_id: String,
                     val ingestion_time: Long
                   ) {}
