package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Information {
    private int integerValue;
    private double doubleValue;
    private String stringValue;
    private List<String> StringArray;
    private LocalDateTime dateObject;

    @JsonCreator
    public Information() {

    }
}
