package com.neel.readers;

public enum DataReaderPropType {

    CSV(CSVDataReaderProp.class);
    private final Class<? extends DataReaderProp> dataReaderPropClass;


    DataReaderPropType(Class<? extends DataReaderProp> dataReaderPropClass) {
        this.dataReaderPropClass = dataReaderPropClass;
    }
    public Class<? extends DataReaderProp> getDataReaderPropClass() {
        return dataReaderPropClass;
    }
}
