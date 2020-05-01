package com.neel.readers.fileReaders;

import com.neel.readers.DataReader;
import com.neel.readers.DataRow;
import com.neel.readers.readerProps.CSVDataReaderProp;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class CSVDataReader implements DataReader {

    BufferedReader reader;

    public CSVDataReader(CSVDataReaderProp csvDataReaderProp) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(csvDataReaderProp.getFilepath()));
    }

    @Override
    public Stream<DataRow> read() {
        try {
            final CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            return parser.getRecords()
                    .stream()
                    .map(record -> new DataRow(new ArrayList<>(record.toMap().values())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    };
}
