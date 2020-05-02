package data_analyzer

trait KafkaMessageMother {
  def getMessage(producerId: String, eventKey: String, payload: String): String = {
    s"""
       |"{"metadata": {"producer_id": "${producerId}",
       |               "size": ${payload.length},
       |               "message_id": "${eventKey}",
       |               "ingestion_time": 1524237281590},
       |               "payload": "${payload}"
       |               }"""".stripMargin
  }
}
