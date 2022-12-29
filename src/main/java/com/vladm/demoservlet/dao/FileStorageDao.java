package com.vladm.demoservlet.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladm.demoservlet.utils.ObjectMapperInstance;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileStorageDao {

    private static ObjectMapper MAPPER = ObjectMapperInstance.getInstance();


    public static <T> void sync(String pathToFile, Map<String, T> localMap, TypeReference<Map<String, T>> typeReference, String fallbackMessage) {
        try {
            File file = readFile(pathToFile);
            if(localMap.isEmpty()) {
                Map<String, T> fileMap = MAPPER.readValue(file, typeReference);
                localMap.putAll(fileMap);
            } else {
                JsonNode jsonNode = MAPPER.valueToTree(localMap);
                FileUtils.write(file, jsonNode.toPrettyString());
            }
        } catch (IOException e) {
            System.out.println(fallbackMessage);
            throw new RuntimeException(e);
        }
    }

    private static File readFile(String pathToFile) throws IOException {
        File file = FileUtils.getFile(pathToFile);

        if(!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileUtils.write(file, "{}");
            file = FileUtils.getFile(pathToFile);
        }

        return file;
    }
}
