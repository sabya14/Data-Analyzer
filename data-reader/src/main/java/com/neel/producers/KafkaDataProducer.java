package com.neel.producers;

import com.neel.models.KafkaMessage;
import com.neel.models.KafkaMessageMetaData;
import com.neel.readers.DataReader;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class KafkaDataProducer {

    private DataReader dataReader;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MetadataGenerator metadataGenerator;

    private String producerId;
    private String producerTopic;

    private Logger logger = LoggerFactory.getLogger(KafkaDataProducer.class);


    public KafkaDataProducer(DataReader dataReader, String producerId, String producerTopic, MetadataGenerator metadataGenerator, KafkaTemplate<String, String> kafkaTemplate) {
        this.dataReader = dataReader;
        this.producerId = producerId;
        this.producerTopic = producerTopic;
        this.metadataGenerator = metadataGenerator;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce() {
        dataReader.read().forEach(line -> {
            String messageId = metadataGenerator.generateUniqueKey();
            long contentLength = line.toString().length();
            long ingestionTime = metadataGenerator.getCurrentTimeMillis();
            KafkaMessageMetaData messageMetadata = new KafkaMessageMetaData(ingestionTime, producerId, messageId, contentLength);
            String kafkaMessage = new KafkaMessage(line.toString(), messageMetadata).getMessageString();
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
