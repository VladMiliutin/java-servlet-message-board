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

/**
 * This class help us with data storage as files.
 * But it lacks real synchronization. What will happen if we have more than 1-10 parallel users?
 * What data is real, which user is new? is this user was deleted? And will be slugish if have a lot of users
 */
public class FileStorageUserDao implements UserDao {

    private final static String PATH_TO_FOLDER = "/home/vladm/IdeaProjects/demo-servlet-git/data";

    private final static Map<String, User> USER_MAP = new HashMap<>();

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
        USER_MAP.put(user.getId(), user);
        sync();
        return user;

    }

    @Override
    public List<User> findAll() {
        sync();
        return new ArrayList<>(USER_MAP.values());
    }

    @Override
    public Optional<User> findOne(String id) {
        sync();
        return Optional.ofNullable(USER_MAP.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        sync();
        return USER_MAP.values()
                .stream()
                .filter(usr -> usr.getName().equals(name))
                .findFirst();

    }

    @Override
    public Optional<User> findByEmail(String email) {
        sync();
        return USER_MAP.values()
                .stream()
                .filter(usr -> usr.getEmail().equals(email))
                .findFirst();

    }

    @Override
    public void delete(String id) {
        sync();
        USER_MAP.remove(id);
        sync();
    }

    @Override
    public User update(User user) {
        sync();
        USER_MAP.put(user.getId(), user);
        sync();
        return user;
    }

    public void sync() {
        try {
            File file = readFile();
            if(USER_MAP.isEmpty() && freshStart){
                Map<String, User> users = mapper.readValue(file, new TypeReference<Map<String, User>>() {});
                USER_MAP.putAll(users);
                freshStart = false;
            } else {
                JsonNode jsonNode = mapper.valueToTree(USER_MAP);
                FileUtils.write(file, jsonNode.toPrettyString());
            }
        } catch (IOException e) {
            System.out.println("User file corrupted");
            throw new RuntimeException(e);
        }
    }

    private static File readFile() throws IOException {
        File file = FileUtils.getFile(PATH_TO_FOLDER + "/users.json");
        if(!file.exists()) {
            file.createNewFile();
            FileUtils.write(file, "{}");
            file = FileUtils.getFile(PATH_TO_FOLDER + "/users.json");
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
