package com.neel.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CSVDataProducerTest {

    @InjectMocks
    CSVDataProducer csvDataProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private MetadataGenerator metadataGenerator;

    @Mock
    Stream<String> fileStream;

    @Mock
    ListenableFuture<SendResult<String, String>> future;

    @Value("${producer.id}")
    String producerId;

    @Value("${producer.topic}")
    String producerTopic;


    @Test
    void shouldAddMetaDataAndSendMessage() throws NoSuchFieldException, IllegalAccessException, IOException {
        Field writeTopic = CSVDataProducer.class.getDeclaredField("producerTopic");
        writeTopic.setAccessible(true);
        writeTopic.set(csvDataProducer, "topic");

        Field producerId = CSVDataProducer.class.getDeclaredField("producerId");
        producerId.setAccessible(true);
        producerId.set(csvDataProducer, "producer");

        fileStream = Stream.of("This is a test", "Second Test");

        String eventKey = "123e4567-e89b-12d3-a456-426655440001";
        when(metadataGenerator.generateUniqueKey()).thenReturn(eventKey);
        when(metadataGenerator.getCurrentTimeMillis()).thenReturn(1524237281590L);
        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);

        csvDataProducer.sendMessage(fileStream);


        verify(kafkaTemplate, times(2)).send(eq("topic"), eq(eventKey), anyString());

        verify(kafkaTemplate).send(
                "topic",
                eventKey,
                "{\"metadata\": {\"producer_id\": \"producer\", " +
                        "\"size\": 14, " +
                        "\"message_id\": \"" + eventKey + "\", " +
                        "\"ingestion_time\": 1524237281590}, " +
                        "\"payload\": This is a test}"
        );
    }
}
