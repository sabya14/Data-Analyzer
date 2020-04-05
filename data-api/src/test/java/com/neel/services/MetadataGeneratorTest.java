package com.neel.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetadataGeneratorTest {

    @Test
    void shouldGenerateUniqueValues() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        String s1 = metadataGenerator.generateUniqueKey();
        String s2 = metadataGenerator.generateUniqueKey();
        assertNotEquals(s1, s2);
    }
}