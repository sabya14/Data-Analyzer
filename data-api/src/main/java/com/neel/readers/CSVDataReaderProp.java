package com.neel.readers;

import com.fasterxml.jackson.annotation.JsonProperty;

import static com.neel.readers.DataReaderPropType.CSV;

public class CSVDataReaderProp extends DataReaderProp {

    String filepath;

    public CSVDataReaderProp(@JsonProperty("filepath") String filepath) {
        super(CSV);
        this.filepath = filepath;
    }

    @Override
    public DataReader getReader() {
        return new CSVDataReader(this);
    }
}
