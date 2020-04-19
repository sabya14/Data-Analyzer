package com.neel.readers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class DataReaderProp {

    protected DataReaderPropType type;
    public abstract DataReader getReader();
}
