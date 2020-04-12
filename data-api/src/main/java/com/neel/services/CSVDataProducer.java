package com.neel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CSVDataProducer {


    private StreamDataProducer streamDataProducer;
    private CSVReader csvReader;
    private List<String> fileNames;

    @Autowired
    public CSVDataProducer(StreamDataProducer streamDataProducer, CSVReader csvReader, @Value("${producer.fileNames}") List<String> fileNames) {
        this.streamDataProducer = streamDataProducer;
        this.csvReader = csvReader;
        this.fileNames = fileNames;
    }

    @PostConstruct
    public void startProducing() {
        fileNames.forEach(fileName -> {
            try {
                Stream<String> lines = csvReader.read(fileName);
                streamDataProducer.sendMessage(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
