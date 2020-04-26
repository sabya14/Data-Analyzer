package com.neel.producers;

import com.neel.readers.DataReader;
import com.neel.readers.DataReaderFactory;
import com.neel.readers.fileReaders.CSVDataReader;
import com.salesforce.kafka.test.KafkaTestUtils;
import com.salesforce.kafka.test.junit5.SharedKafkaTestResource;
import com.salesforce.kafka.test.listeners.PlainListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


@SpringBootTest
class KafkaDataProducerTestIT {
    @RegisterExtension
    public static final SharedKafkaTestResource sharedKafkaTestResource = new SharedKafkaTestResource()
            .withBrokers(3)
            .registerListener(new PlainListener().onPorts(9093, 9094));


    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    MetadataGenerator metadataGenerator;

    KafkaDataProducer kafkaDataProducer;


    @Test
    public void shouldWriteDataToTopic() throws Exception {


        String path = KafkaDataProducerTestIT.class.getClassLoader().getResource("input.csv").getPath();
        DataReader reader = new DataReaderFactory().getReader("{\"type\": \"CSV\", \"filepath\":\"" + path + "\"}");
        CSVDataReader csvDataReader = (CSVDataReader) reader;

        String topicName = "bus_data";
        String topicId = "some_id";

        kafkaDataProducer = new KafkaDataProducer(reader, topicId, topicName, metadataGenerator, kafkaTemplate);
        kafkaDataProducer.produce();
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