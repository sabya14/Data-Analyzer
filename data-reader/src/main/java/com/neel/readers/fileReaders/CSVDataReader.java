package com.neel.readers.fileReaders;

import com.neel.readers.DataReader;
import com.neel.readers.DataRow;
import com.neel.readers.readerProps.CSVDataReaderProp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

public class CSVDataReader implements DataReader {

    BufferedReader reader;

    public CSVDataReader(CSVDataReaderProp csvDataReaderProp) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(csvDataReaderProp.getFilepath()));
    }

    @Override
    public Stream<DataRow> read() {
        return reader.lines().map(DataRow::new);
    };
}
