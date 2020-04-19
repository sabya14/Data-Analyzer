package com.neel.readers;


import java.util.stream.Stream;

public interface DataReader {
    public Stream<DataRow> read();
}
