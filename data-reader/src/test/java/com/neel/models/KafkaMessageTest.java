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
        String message = "{\"metadata\": " + "test_metadata" + ", \"payload\": " + "[\"1\",\"Test\",\"12\"]" + "}";
        String kafkaMessage = new KafkaMessage("[\"1\",\"Test\",\"12\"]", kafkaMessageMetaData).getMessageString();
        assertEquals(kafkaMessage, message);
    }

}