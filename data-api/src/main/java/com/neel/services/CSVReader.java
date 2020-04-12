package com.neel.services;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


@Component
public class CSVReader {
    public Stream<String> read(String fileName) throws IOException {
        URL resource = this.getClass().getClassLoader().getResource(fileName);
        String filepath = resource.getPath();
        return Files.lines(Paths.get(filepath));
    }
}
