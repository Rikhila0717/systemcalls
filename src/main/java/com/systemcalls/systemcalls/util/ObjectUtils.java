package com.systemcalls.systemcalls.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtils {
    public static String serialize(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle the exception
            System.out.println("Error occurred while serializing: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            // Handle the exception
            System.out.println("Error occurred while deserializing: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }
}

