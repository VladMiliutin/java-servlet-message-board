package com.vladm.demoservlet.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperInstance {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance(){
        return objectMapper;
    }
}