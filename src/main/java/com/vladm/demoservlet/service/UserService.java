package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.exception.UserExistsException;
import com.vladm.demoservlet.model.User;

import java.util.*;

public class UserService {

    private final UserDao userDao = new FileStorageUserDao();

    public User createUser(String name, String email) {
        if(userExists(name, email)){
           throw new UserExistsException();
        }

        final String id = UUID.randomUUID().toString();
        User user = new User(id, name, email);
        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    public void deleteUser(String id) {
        userDao.delete(id);
    }

    public Optional<User> findUserByName(String name) {
        return userDao.findByName(name);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean userExists(String name, String email){
        Optional<User> usrOptional = userDao.findAll()
                .stream()
                .filter(usr -> usr.getEmail().equals(email) || usr.getName().equals(name))
                .findFirst();

        return usrOptional.isPresent();
    }
}
