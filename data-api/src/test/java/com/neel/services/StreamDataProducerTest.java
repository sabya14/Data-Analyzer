package com.neel.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class StreamDataProducerTest {

    private StreamDataProducer streamDataProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private MetadataGenerator metadataGenerator;

    @Mock
    Stream<String> fileStream;

    @Mock
    ListenableFuture<SendResult<String, String>> future;

    String producerId;
    String producerTopic;

    @BeforeEach
    void setUp() {
        producerId = "producerId";
        producerTopic = "producerTopic";
        streamDataProducer = new StreamDataProducer(kafkaTemplate, metadataGenerator, producerId, producerTopic);
    }

    @Test
    void shouldAddMetaDataAndSendMessage() throws NoSuchFieldException, IllegalAccessException, IOException {
        fileStream = Stream.of("This is a test", "Second Test");
        String eventKey = "123e4567-e89b-12d3-a456-426655440001";
        when(metadataGenerator.generateUniqueKey()).thenReturn(eventKey);
        when(metadataGenerator.getCurrentTimeMillis()).thenReturn(1524237281590L);
        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);

        streamDataProducer.sendMessage(fileStream);


        verify(kafkaTemplate, times(2)).send(eq(producerTopic), eq(eventKey), anyString());

        verify(kafkaTemplate).send(
                producerTopic,
                eventKey,
                "{\"metadata\": {\"producer_id\": \"" + producerId + "\", " +
                        "\"size\": 14, " +
                        "\"message_id\": \"" + eventKey + "\", " +
                        "\"ingestion_time\": 1524237281590}, " +
                        "\"payload\": This is a test}"
        );
    }
}