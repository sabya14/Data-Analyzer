package com.neel.kakfa;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.brokers}")
    private String kafkaBrokers;

    @Value("${producer.topic}")
    private String topic;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokers);
        return new KafkaAdmin(props);
    }

    @Bean
    public NewTopic test() {
        return TopicBuilder.name(topic)
                .partitions(2)
                .replicas(2)
                .build();
    }
}
