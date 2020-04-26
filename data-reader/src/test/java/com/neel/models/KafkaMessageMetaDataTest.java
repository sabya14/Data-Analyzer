package com.neel.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KafkaMessageMetaDataTest {

    @Test
    void shouldReturnCorrectToString() {
        String producerId = "test";
        Long size = 100L;
        String uuid = UUID.randomUUID().toString();
        Long ingestionTime = 1111L;

        KafkaMessageMetaData kafkaMessageMetaData = new KafkaMessageMetaData(ingestionTime, producerId, uuid, size);

        String message = "{" +
                "\"producer_id\": \"" + producerId + "\", " +
                "\"size\": " + size + ", " +
                "\"message_id\": \"" + uuid + "\", " +
                "\"ingestion_time\": " + ingestionTime +
                "}";
        assertEquals(message, kafkaMessageMetaData.toString());
    }

}