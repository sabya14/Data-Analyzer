package com.neel.readers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataRow{
    String data;



    @Override
    public String toString() {
        return data;
    }
}
