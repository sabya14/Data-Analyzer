package com.neel.models;

public class KafkaMessageMetaData {
    private long ingestionTime;
    private String producerId;
    private String messageUUID;
    private long size;

    public KafkaMessageMetaData(long ingestionTime, String producerId, String messageUUID, long size) {
        this.ingestionTime = ingestionTime;
        this.producerId = producerId;
        this.messageUUID = messageUUID;
        this.size = size;
    }

    @Override
    public String toString() {
        return "{" +
                "\"producer_id\": \"" + this.producerId + "\", " +
                "\"size\": " + this.size + ", " +
                "\"message_id\": \"" + this.messageUUID + "\", " +
                "\"ingestion_time\": " + this.ingestionTime +
                "}";
    }
}
