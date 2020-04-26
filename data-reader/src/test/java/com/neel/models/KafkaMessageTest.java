package com.neel.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KafkaMessageTest {
    @Test
    void shouldReturnMessage() {
        KafkaMessageMetaData kafkaMessageMetaData = mock(KafkaMessageMetaData.class);
        when(kafkaMessageMetaData.toString()).thenReturn("test_metadata");
        String message = "{\"metadata\": " + "test_metadata" + ", \"payload\": " + "message" + "}";
        String kafkaMessage = new KafkaMessage("message", kafkaMessageMetaData).getMessageString();
        assertEquals(kafkaMessage, message);
    }

}