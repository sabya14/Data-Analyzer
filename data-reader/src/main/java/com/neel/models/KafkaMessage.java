package com.neel.models;

import com.google.gson.Gson;

public class KafkaMessage {
    private String message;
    private KafkaMessageMetaData metadata;

    public KafkaMessage(String message, KafkaMessageMetaData metadata) {
        this.message = message;
        this.metadata = metadata;
    }

    public String getMessageString() {
        return "{\"metadata\": " + metadata.toString() + ", \"payload\": " + message + "}";
    }

}


