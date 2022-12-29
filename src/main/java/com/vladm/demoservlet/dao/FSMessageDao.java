package com.vladm.demoservlet.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vladm.demoservlet.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FSMessageDao implements MessageDao{

    private final static String PATH_TO_FILE = System.getProperty("DATA_FOLDER") + "/messages.json";

    private final Map<String, Message> messages = new ConcurrentHashMap<>();
    

    private static FSMessageDao INSTANCE;

    public FSMessageDao() {
    }

    @Override
    public Message save(Message message) {
        sync();
        messages.put(message.getId(), message);
        sync();
        return message;
    }

    @Override
    public List<Message> findAll() {
        sync();
        return new ArrayList<>(messages.values());
    }

    @Override
    public Optional<Message> findOne(String id) {
        sync();
        return Optional.ofNullable(messages.get(id));
    }

    @Override
    public List<Message> findByUserId(String id) {
        sync();
        return messages.values().stream()
                .filter(msg -> msg.getUserId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> findByIds(List<String> ids) {
        sync();
        return messages.values().stream()
                .filter(msg -> ids.contains(msg.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Message update(Message message) {
        return save(message);
    }

    @Override
    public void delete(String id) {
        sync();
        messages.remove(id);
        sync();
    }


    public void sync() {
        FileStorageDao.sync(PATH_TO_FILE, messages, new TypeReference<>() {}, "Message file corrupted");
    }

    public static FSMessageDao getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FSMessageDao();
        }

        return INSTANCE;
    }
}
