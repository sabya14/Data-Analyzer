package com.neel.services;

import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.RecordProducer;
import net.mguenther.kafka.junit.TopicConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.ListenableFuture;

import java.lang.reflect.Field;

import static net.mguenther.kafka.junit.EmbeddedKafkaCluster.provisionWith;
import static net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig.useDefaults;
import static net.mguenther.kafka.junit.Wait.delay;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DataProducerTest {

    @InjectMocks
    DataProducer dataProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private MetadataGenerator metadataGenerator;

    @Mock
    ListenableFuture<SendResult<String, String>> future;

    @Value("${producer.id}")
    String producerId;

    @Value("${producer.topic}")
    String producerTopic;

    @Test
    void shouldAddMetaDataAndSendMessage() throws NoSuchFieldException, IllegalAccessException {
        Field writeTopic = DataProducer.class.getDeclaredField("producerTopic");
        writeTopic.setAccessible(true);
        writeTopic.set(dataProducer, "topic");

        Field producerId = DataProducer.class.getDeclaredField("producerId");
        producerId.setAccessible(true);
        producerId.set(dataProducer, "producer");


        HttpEntity<String> response = mock(HttpEntity.class, Answers.RETURNS_DEEP_STUBS);
        when(response.getBody()).thenReturn("LargeJsonMessage");
        when(response.getHeaders().getContentLength()).thenReturn(1234L);
        String eventKey = "123e4567-e89b-12d3-a456-426655440001";
        when(metadataGenerator.generateUniqueKey()).thenReturn(eventKey);
        when(metadataGenerator.getCurrentTimeMillis()).thenReturn(1524237281590L);
        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);

        dataProducer.sendMessage(response);
        verify(kafkaTemplate).send(
                "topic",
                eventKey,
                "{\"metadata\": {\"producer_id\": \"producer\", " +
                        "\"size\": 1234, " +
                        "\"message_id\": \"" + eventKey + "\", " +
                        "\"ingestion_time\": 1524237281590}, " +
                        "\"payload\": LargeJsonMessage}"
        );
    }
}