package com.neel.data_analyzer.utils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReservoirSamplingTest {

    @Test
    void shouldReturnApproxRows() throws IOException {
        String path = CSVReservoirSamplingTest.class.getClassLoader().getResource("inputTest.csv").getPath();
        CSVReservoirSampling csvReservoirSampling = new CSVReservoirSampling(path);
        FileChannel channel = new FileInputStream(path).getChannel();
        long approxRows = csvReservoirSampling.getApproxRows(channel.size());
        assertEquals(23, approxRows);
    }

    @Test
    void shouldFetchNextCorrectRowGivenAnOffset() throws IOException {
        String path = CSVReservoirSamplingTest.class.getClassLoader().getResource("inputTest.csv").getPath();
        CSVReservoirSampling csvReservoirSampling = new CSVReservoirSampling(path);
        FileChannel channel = new FileInputStream(path).getChannel();
        long size = channel.size();
        long approxRows = csvReservoirSampling.getApproxRows(size);
        long randomOffset = channel.size() / approxRows;
        String nextValidRow = csvReservoirSampling.getNextValidRow(path, randomOffset);
        assertEquals("1,12,Neel", nextValidRow);
    }

    @Test
    void shouldFetchNextConsecutiveCorrectRowGivenAnOffset() throws IOException {
        String path = CSVReservoirSamplingTest.class.getClassLoader().getResource("inputTest.csv").getPath();
        CSVReservoirSampling csvReservoirSampling = new CSVReservoirSampling(path);
        FileChannel channel = new FileInputStream(path).getChannel();
        long size = channel.size();
        long approxRows = csvReservoirSampling.getApproxRows(size);
        long randomOffset = channel.size() / approxRows;
        List<String> consecutiveValidRows = csvReservoirSampling.getConsecutiveValidRows(path, randomOffset, 5);
        assertEquals(consecutiveValidRows.size(), 5);
    }


    @Test
    void shouldReturnRandomRows() throws IOException {
        String path = CSVReservoirSamplingTest.class.getClassLoader().getResource("mta_1706.csv").getPath();
        CSVReservoirSampling csvReservoirSampling = new CSVReservoirSampling(path);
        List<String> rows = csvReservoirSampling.getRows(path);
        assertEquals(rows.size(), 500);
    }
}