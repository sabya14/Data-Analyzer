package com.neel.services;


import com.neel.models.KafkaMessage;
import com.neel.models.KafkaMessageMetaData;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class CSVDataProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private MetadataGenerator metadataGenerator;
    private Stream<String> fileStream;


    @Value("${producer.id}")
    String producerId;

    @Value("${producer.topic}")
    String producerTopic;

    Logger logger = LoggerFactory.getLogger(CSVDataProducer.class);

    @Autowired
    public CSVDataProducer(KafkaTemplate<String, String> kafkaTemplate, MetadataGenerator metadataGenerator) {
        this.kafkaTemplate = kafkaTemplate;
        this.metadataGenerator = metadataGenerator;
    }

    public void sendMessage(Stream<String> lines) throws IOException {
        fileStream = lines;
        fileStream.forEach(line -> {
            String messageId = metadataGenerator.generateUniqueKey();
            long contentLength = line.length();
            long ingestionTime = metadataGenerator.getCurrentTimeMillis();
            KafkaMessageMetaData messageMetadata = new KafkaMessageMetaData(ingestionTime, producerId, messageId, contentLength);
            String kafkaMessage = new KafkaMessage(line, messageMetadata).getMessageString();

            ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(producerTopic, messageId, kafkaMessage);

            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    RecordMetadata metadata = result.getRecordMetadata();
                    logger.info("Sent message" + metadata.topic() + " Partition:" + metadata.partition() + " Offset: " + metadata.offset() + " key: " + messageId);
                }

                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Failed");
                }

            });
        });
    }
}