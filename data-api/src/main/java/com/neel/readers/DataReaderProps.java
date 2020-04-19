package com.neel.readers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.FileNotFoundException;

@AllArgsConstructor
@Getter
public abstract class DataReaderProps {
    protected DataReaderPropType type;
    public abstract DataReader getReader() throws FileNotFoundException;
}
