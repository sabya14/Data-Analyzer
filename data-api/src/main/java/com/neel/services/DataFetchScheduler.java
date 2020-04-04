package com.neel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class DataFetchScheduler {

    @Value("${producer.url}")
    private String url;

    private final DataProducer dataProducer;

    public DataFetchScheduler(DataProducer dataProducer) {
        this.dataProducer = dataProducer;
    }

    @Scheduled(cron="${producer.cron}")
    public void scheduledProducer() {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> response = template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        dataProducer.sendMessage(response);
    }
}
