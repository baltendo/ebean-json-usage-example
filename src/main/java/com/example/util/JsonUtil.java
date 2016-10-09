package com.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

public class JsonUtil {
    private static ObjectMapper defaultMapper;

    static {
        defaultMapper = new ObjectMapper();
        defaultMapper.registerModule(new JavaTimeModule());
    }

    @SuppressWarnings(value = "unchecked")
    public static Map<String, Object> toJsonMap(Object object) {
        return defaultMapper.convertValue(object, Map.class);
    }

    public static Object fromJsonMap(Map<String, Object> data, Class c) {
        return defaultMapper.convertValue(data, c);
    }

    private JsonUtil() {

    }
}
