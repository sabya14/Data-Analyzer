package com.neel.readers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DataRow{
    List<String> data;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(data);
    }
}
