package com.neel.services;

import com.salesforce.kafka.test.KafkaTestUtils;
import com.salesforce.kafka.test.junit5.SharedKafkaTestResource;
import com.salesforce.kafka.test.listeners.PlainListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


@SpringBootTest
class CSVDataProducerTest {

    @RegisterExtension
    public static final SharedKafkaTestResource sharedKafkaTestResource = new SharedKafkaTestResource()
            .withBrokers(2)
            .registerListener(new PlainListener().onPorts(9093, 9094));

    @Autowired
    CSVDataProducer csvDataProducer;

    @BeforeAll
    static void setupKafkaCluster() {
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    public void shouldWriteDataToTopic() {
        csvDataProducer.startProducing();
        String topicName = "bus_data";

        await()
                .atMost(15, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    KafkaTestUtils kafkaTestUtils = sharedKafkaTestResource.getKafkaTestUtils();
                    List<ConsumerRecord<String, String>> allMessages = kafkaTestUtils.consumeAllRecordsFromTopic(topicName, StringDeserializer.class, StringDeserializer.class);
                    System.out.println(allMessages);
                    assertThat(allMessages).isNotEmpty();
                });
    }

}
