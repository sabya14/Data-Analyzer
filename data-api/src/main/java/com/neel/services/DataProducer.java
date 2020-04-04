package com.neel.services;


import com.neel.models.KafkaMessage;
import com.neel.models.KafkaMessageMetaData;
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
    private MetadataGenerator metadataGenerator;

    @Value("${producer.id}")
    String producerId;

    @Value("${producer.topic}")
    String producerTopic;

    @Autowired
    public DataProducer(KafkaTemplate<String, String> kafkaTemplate, MetadataGenerator metadataGenerator) {
        this.kafkaTemplate = kafkaTemplate;
        this.metadataGenerator = metadataGenerator;
    }

    public void sendMessage(HttpEntity<String> response) {
        String message = response.getBody();
        long contentLength = response.getHeaders().getContentLength();
        String messageId = metadataGenerator.generateUniqueKey();
        long ingestionTime = metadataGenerator.getCurrentTimeMillis();

        KafkaMessageMetaData messageMetadata = new KafkaMessageMetaData(ingestionTime, producerId, messageId, contentLength);
        String kafkaMessage = new KafkaMessage(message, messageMetadata).getMessageString();
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(producerTopic, messageId, kafkaMessage);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Failed");
            }

        });
    }
}
