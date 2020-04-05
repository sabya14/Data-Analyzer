package com.neel.services;


import com.neel.models.KafkaMessage;
import com.neel.models.KafkaMessageMetaData;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class DataProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProducer<String, String> kafkaProducer;
    private MetadataGenerator metadataGenerator;

    @Value("${producer.id}")
    String producerId;

    @Value("${producer.topic}")
    String producerTopic;

    Logger logger = LoggerFactory.getLogger(DataProducer.class);

    @Autowired
    public DataProducer(KafkaTemplate<String, String> kafkaTemplate, MetadataGenerator metadataGenerator, KafkaProducer<String, String> kafkaProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.metadataGenerator = metadataGenerator;
        this.kafkaProducer = kafkaProducer;
    }

    private String getMessage(HttpEntity<String> response, String messageId) {
        String message = response.getBody();
        long contentLength = response.getHeaders().getContentLength();
        long ingestionTime = metadataGenerator.getCurrentTimeMillis();
        KafkaMessageMetaData messageMetadata = new KafkaMessageMetaData(ingestionTime, producerId, messageId, contentLength);
        return new KafkaMessage(message, messageMetadata).getMessageString();
    }

    public void sendMessage(HttpEntity<String> response) {
        String messageId = metadataGenerator.generateUniqueKey();
        String kafkaMessage = getMessage(response, messageId);
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
    }

    public void sendMessageBySimpleProducer(HttpEntity<String> response) {
        String messageId = metadataGenerator.generateUniqueKey();
        String kafkaMessage = getMessage(response, messageId);
        ProducerRecord<String, String> record = new ProducerRecord<>(producerTopic, messageId, kafkaMessage);
        kafkaProducer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    logger.info("Sent message" + metadata.topic() + " Partition:" + metadata.partition() + " Offset: " + metadata.offset() + " key: " + messageId);
                } else {
                    logger.info("Message sending failed" + exception);
                }

            }
        });
    }
}
