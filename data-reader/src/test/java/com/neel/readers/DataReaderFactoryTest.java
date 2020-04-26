package com.neel.readers;

import com.neel.readers.fileReaders.CSVDataReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataReaderFactoryTest {

    @Test
    void shouldReturnCorrectDataPropertiesClassBasedOnTypePassed() throws Exception {
        String path = DataReaderFactoryTest.class.getClassLoader().getResource("input.csv").getPath();
        DataReader reader = new DataReaderFactory().getReader("{\"type\": \"CSV\", \"filepath\":\"" + path + "\"}");
        assertEquals(reader.getClass().toString(), CSVDataReader.class.toString());
    }

}