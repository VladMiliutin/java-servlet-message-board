package com.vladm.demoservlet.service;

import com.vladm.demoservlet.model.User;

import java.util.*;

public class UserService {

    public final static Map<String, User> USER_MAP = new HashMap<>();

    public User createUser(String name, String email) {
        final String id = UUID.randomUUID().toString();
        User user = new User(id, name, email);
        USER_MAP.put(id, user);
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(USER_MAP.values());
    }

    public void deleteUser(String id) {
        USER_MAP.remove(id);
    }

    public Optional<User> findUserByName(String name) {
        return USER_MAP.values()
                .stream()
                .filter(usr -> usr.getName().equals(name))
                .findFirst();
    }

    public Optional<User> findUserByEmail(String email) {
        return USER_MAP.values()
                .stream()
                .filter(usr -> usr.getEmail().equals(email))
                .findFirst();
    }
}
