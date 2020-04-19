package com.neel.readers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.FileNotFoundException;
import java.util.HashMap;

@AllArgsConstructor
public class DataReaderFactory {
    public DataReader getReader(String dataProps) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap props = objectMapper.readValue(dataProps, HashMap.class);
        String type = (String) props.get("type");
        DataReaderPropType readerType = DataReaderPropType.valueOf(type);
        Class<? extends DataReaderProps> readerPropsClazz = readerType.getDataReaderPropClass();
        DataReaderProps dataReaderProps = objectMapper.readValue(dataProps, readerPropsClazz);
        try {
            return dataReaderProps.getReader();
        } catch (FileNotFoundException e) {
            throw new Exception("Internal error");
        }
    }
}
