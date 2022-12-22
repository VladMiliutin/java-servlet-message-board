package com.vladm.demoservlet.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.util.ObjectMapperInstance;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class help us with data storage as files.
 * But it lacks real synchronization. What will happen if we have more than 1-10 parallel users?
 * What data is real, which user is new? is this user was deleted? And will be slugish if have a lot of users
 */
public class FileStorageUserDao implements UserDao {

    private final static String FILE = System.getProperty("FILE_STORAGE_PATH") + "/users.json";

    private final Map<String, User> usersMap = new ConcurrentHashMap<>();

    private final ObjectMapper mapper;

    // Helps us to understand if map is empty because server restarted or because all users are gone
    private boolean freshStart = true;

    private static FileStorageUserDao INSTANCE;

    public FileStorageUserDao(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        sync();
        usersMap.put(user.getId(), user);
        sync();
        return user;

    }

    @Override
    public List<User> findAll() {
        sync();
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public Optional<User> findOne(String id) {
        sync();
        return Optional.ofNullable(usersMap.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        sync();
        return usersMap.values()
                .stream()
                .filter(usr -> usr.getName().equals(name))
                .findFirst();

    }

    @Override
    public Optional<User> findByEmail(String email) {
        sync();
        return usersMap.values()
                .stream()
                .filter(usr -> usr.getEmail().equals(email))
                .findFirst();

    }

    @Override
    public void delete(String id) {
        sync();
        usersMap.remove(id);
        sync();
    }

    @Override
    public User update(User user) {
        sync();
        usersMap.put(user.getId(), user);
        sync();
        return user;
    }

    public void sync() {
        try {
            File file = readFile();
            if(usersMap.isEmpty() && freshStart){
                Map<String, User> users = mapper.readValue(file, new TypeReference<Map<String, User>>() {});
                usersMap.putAll(users);
                freshStart = false;
            } else {
                JsonNode jsonNode = mapper.valueToTree(usersMap);
                FileUtils.write(file, jsonNode.toPrettyString());
            }
        } catch (IOException e) {
            System.out.println("User file corrupted");
            throw new RuntimeException(e);
        }
    }

    private static File readFile() throws IOException {
        File file = FileUtils.getFile(FILE);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileUtils.write(file, "{}");
            file = FileUtils.getFile(FILE );
        }

        return file;
    }

    public static FileStorageUserDao getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new FileStorageUserDao(ObjectMapperInstance.getInstance());
        }
        return INSTANCE;
    }
}
