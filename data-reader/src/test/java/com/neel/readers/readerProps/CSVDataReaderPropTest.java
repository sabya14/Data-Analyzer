package com.neel.readers.readerProps;

import com.neel.readers.fileReaders.CSVDataReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVDataReaderPropTest {

    @Test
    void shouldReturnCSVReaderWhenCorrectPropsGiven() throws FileNotFoundException {
        String path = CSVDataReaderPropTest.class.getClassLoader().getResource("test.csv").getPath();
        CSVDataReaderProp csvDataReaderProp = new CSVDataReaderProp(path);
        assertEquals(csvDataReaderProp.getReader().getClass(), CSVDataReader.class);
    }
}