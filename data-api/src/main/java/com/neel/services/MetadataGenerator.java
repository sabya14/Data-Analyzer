package com.neel.services;

import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MetadataGenerator {
    long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}
