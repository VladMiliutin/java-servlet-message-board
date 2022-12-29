package com.vladm.demoservlet.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperInstance {

    private static ObjectMapper INSTANCE = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }
}
