package com.neel.readers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class DataReaderFactory {
    public DataReader getReader(String dataProps) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap props = objectMapper.readValue(dataProps, HashMap.class);
        String type = (String) props.get("type");
        DataReaderPropType readerType = DataReaderPropType.valueOf(type);
        Class<? extends DataReaderProp> readerPropsClazz = readerType.getDataReaderPropClass();
        DataReaderProp dataReaderProp = objectMapper.readValue(dataProps, readerPropsClazz);
        return dataReaderProp.getReader();
    };
}
