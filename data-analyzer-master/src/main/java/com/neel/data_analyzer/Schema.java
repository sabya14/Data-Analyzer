package com.neel.data_analyzer;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@Getter
public class Schema {
    HashMap<Integer, List<String>> schemaDef;
}
