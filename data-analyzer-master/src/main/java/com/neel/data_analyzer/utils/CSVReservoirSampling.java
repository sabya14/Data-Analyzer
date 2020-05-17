package com.neel.data_analyzer.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CSVReservoirSampling {
    String filePath;

    public CSVReservoirSampling(String filePath) {
        this.filePath = filePath;
    }

    public long getApproxRows(long fileSize) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        double singleRowSize = bufferedReader.lines().limit(500).mapToInt(x -> x.getBytes().length).average().getAsDouble();
        return (long) (fileSize / singleRowSize);
    }

    private RandomAccessFile getRandomAccessFile(String filePath, long randomOffset) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filePath, "r");
        file.seek(randomOffset);
        // Iter till next newline
        long goAhead = 0;
        while (file.readByte() != 10) {
            goAhead++;
        }
        // Put pointer on next line and return next line.
        file.seek(randomOffset + goAhead + 1);
        return file;
    }


    public String getNextValidRow(String filePath, long randomOffset) throws IOException {
        RandomAccessFile file = getRandomAccessFile(filePath, randomOffset);
        String line = file.readLine();
        file.close();
        return line;
    }

    public List<String> getConsecutiveValidRows(String filePath, long randomOffset, int noOfRows) throws IOException {
        RandomAccessFile file = getRandomAccessFile(filePath, randomOffset);
        ArrayList<String> result = new ArrayList<String>();
        while (noOfRows != 0) {
            result.add(file.readLine());
            noOfRows--;
        }
        file.close();
        return result;
    }

    private List<Long> getSortedOffsets(long fileSize, int toSelect, long afterApproxToSelectRows) {
        return new Random().longs(afterApproxToSelectRows, fileSize)
                .distinct()
                .limit(Math.min(toSelect, fileSize - afterApproxToSelectRows))
                .sorted().boxed().collect(Collectors.toList());
    }


    public List<String> getRows(String filePath) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        long fileSize = raf.length();
        long approxRows = getApproxRows(fileSize);
        int minimumRowThreshold = 500;
        int toSelect = (int) Math.min(minimumRowThreshold, approxRows);
        long afterApproxToSelectRows = toSelect * (fileSize / approxRows);
        List<Long> sortedOffsets = getSortedOffsets(fileSize, toSelect, afterApproxToSelectRows);

        Set<String> randomRows = sortedOffsets
                .parallelStream()
                .map(x -> {
                    try {
                        return getNextValidRow(filePath, x);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .collect(Collectors.toSet());

        List<String> rows = Stream.concat(
                randomRows.stream(),
                getConsecutiveValidRows(filePath, 1, toSelect - randomRows.size()).stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return rows;
    }
}
