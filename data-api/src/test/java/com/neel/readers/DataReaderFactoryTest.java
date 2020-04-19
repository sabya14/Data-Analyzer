package com.neel.readers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataReaderFactoryTest {

    @Test
    void shouldReturnCorrectDataPropertiesClassBasedOnTypePassed() throws JsonProcessingException {
        String dataProps = "{\"type\": \"CSV\", \"filepath\": \"somepath\"}";
        DataReader dataReader = new DataReaderFactory().getReader(dataProps);
        assertEquals(dataReader.getClass().toString(), CSVDataReader.class.toString());
    }

}