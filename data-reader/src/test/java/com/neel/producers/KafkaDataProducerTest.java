package com.neel.producers;

import com.neel.readers.DataReader;
import com.neel.readers.DataRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaDataProducerTest {

    private KafkaDataProducer dataProducer;

    @Mock
    private MetadataGenerator metadataGenerator;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    DataReader dataReader;


    @Mock
    ListenableFuture<SendResult<String, String>> future;

    String producerId;
    String producerTopic;


    @BeforeEach
    void setUp() {
        producerId = "producerId";
        producerTopic = "producerTopic";
        dataProducer = new KafkaDataProducer(dataReader, producerId, producerTopic, metadataGenerator, kafkaTemplate);
    }

    @Test
    void shouldSendMessageToKafka(){
        String eventKey = "123e4567-e89b-12d3-a456-426655440001";
        when(metadataGenerator.generateUniqueKey()).thenReturn(eventKey);
        when(metadataGenerator.getCurrentTimeMillis()).thenReturn(1524237281590L);
        when(kafkaTemplate.send(any(), any(), any())).thenReturn(future);
        Stream<DataRow> readerOutput = Stream.of(new DataRow("This is a data"),new DataRow("This is another data"));
        when(dataReader.read()).thenReturn(readerOutput);
        dataProducer.produce();
        verify(kafkaTemplate, times(2)).send(eq(producerTopic), eq(eventKey), anyString());

        verify(kafkaTemplate).send(
                producerTopic,
                eventKey,
                "{\"metadata\": {\"producer_id\": \"" + producerId + "\", " +
                        "\"size\": 14, " +
                        "\"message_id\": \"" + eventKey + "\", " +
                        "\"ingestion_time\": 1524237281590}, " +
                        "\"payload\": This is a data}"
        );
    }


}