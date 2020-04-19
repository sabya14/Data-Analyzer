package com.neel.readers;

import com.neel.readers.readerProps.CSVDataReaderProp;

public enum DataReaderPropType {

    CSV(CSVDataReaderProp.class);
    private final Class<? extends DataReaderProps> dataReaderPropClass;


    DataReaderPropType(Class<? extends DataReaderProps> dataReaderPropClass) {
        this.dataReaderPropClass = dataReaderPropClass;
    }
    public Class<? extends DataReaderProps> getDataReaderPropClass() {
        return dataReaderPropClass;
    }
}
