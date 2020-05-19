package com.neel.data_analyzer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@AllArgsConstructor
public class CSVMapper {
    List<String> rows;

    public List<List<String>> parseRows() {
        CsvSchema csvSchema = CsvSchema.emptySchema().withoutHeader();
        ObjectReader mapper = new CsvMapper().reader(List.class).with(csvSchema);

        return this.rows.stream()
                .map(row -> parseOneRow(mapper, row))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<String> parseOneRow(ObjectReader mapper, String row) {
        try {
            return mapper.readValue(row);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
