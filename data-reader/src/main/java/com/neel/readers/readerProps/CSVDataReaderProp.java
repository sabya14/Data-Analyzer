package com.neel.readers.readerProps;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neel.readers.DataReader;
import com.neel.readers.DataReaderProps;
import com.neel.readers.fileReaders.CSVDataReader;
import lombok.Getter;

import java.io.FileNotFoundException;

import static com.neel.readers.DataReaderPropType.CSV;

@Getter
public class CSVDataReaderProp extends DataReaderProps {

    String filepath;

    public CSVDataReaderProp(@JsonProperty("filepath") String filepath) {
        super(CSV);
        this.filepath = filepath;
    }

    @Override
    public DataReader getReader() throws FileNotFoundException {
        return new CSVDataReader(this);
    }
}
