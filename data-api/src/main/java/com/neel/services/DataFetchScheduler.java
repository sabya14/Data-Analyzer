package com.neel.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;


// Enable when polling for data files.
//@Component
public class DataFetchScheduler {

    @Value("${producer.url}")
    private String url;

    private final HTTPDataProducer HTTPDataProducer;

    public DataFetchScheduler(HTTPDataProducer HTTPDataProducer) {
        this.HTTPDataProducer = HTTPDataProducer;
    }

    @Scheduled(cron="${producer.cron}")
    public void scheduledProducer() {
        RestTemplate template = new RestTemplate();
        HttpEntity<String> response = template.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        HTTPDataProducer.sendMessage(response);
    }
}
