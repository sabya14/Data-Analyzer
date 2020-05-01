package com.neel.readers.fileReaders;

import com.neel.readers.DataRow;
import com.neel.readers.readerProps.CSVDataReaderProp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CSVDataReaderPropTestCSVDataReaderTest {

    @Mock
    CSVDataReaderProp csvDataReaderProp;

    @Test
    void shouldReturnStreamOfRowWhenRead() throws FileNotFoundException {
        String path = CSVDataReaderPropTestCSVDataReaderTest.class.getClassLoader().getResource("test.csv").getPath();
        when(csvDataReaderProp.getFilepath()).thenReturn(path);
        CSVDataReader csvDataReader = new CSVDataReader(csvDataReaderProp);
        Stream<DataRow> lines = csvDataReader.read();
        Optional<DataRow> first = lines.findFirst();
        assertEquals(first.get().toString(), "[\"1\",\"12\",\"Neel\"]");
    }

}