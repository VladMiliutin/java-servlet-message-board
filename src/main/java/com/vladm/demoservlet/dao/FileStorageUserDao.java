package com.vladm.demoservlet.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vladm.demoservlet.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class FileStorageUserDao implements UserDao {

    private final static String PATH_TO_FILE = System.getProperty("DATA_FOLDER") + "/users.json";

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    private static FileStorageUserDao INSTANCE;

    public FileStorageUserDao() {}

    @Override
    public User save(User user) {
        sync();
        userMap.put(user.getId(), user);
        sync();
        return user;
    }

    @Override
    public List<User> findAll() {
        sync();
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<User> findOne(String id) {
        sync();
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        sync();
        return userMap.values()
                .stream()
                .filter(usr -> usr.getName().equals(name))
                .findFirst();

    }

    @Override
    public Optional<User> findByEmail(String email) {
        sync();
        return userMap.values()
                .stream()
                .filter(usr -> usr.getEmail().equals(email))
                .findFirst();

    }

    @Override
    public User update(User user) {
       return save(user);
    }

    @Override
    public void delete(String id) {
        sync();
        userMap.remove(id);
        sync();
    }

    @Override
    public boolean userExists(User user) {
        sync();
        return userMap.values()
                .stream()
                .anyMatch(usr -> usr.getEmail().equals(user.getEmail()) || usr.getName().equals(user.getName()));

    }

    public void sync() {
        FileStorageDao.sync(PATH_TO_FILE, userMap, new TypeReference<>() {}, "User file corrupted");
    }

    public static FileStorageUserDao getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FileStorageUserDao();
        }

        return INSTANCE;
    }
}
