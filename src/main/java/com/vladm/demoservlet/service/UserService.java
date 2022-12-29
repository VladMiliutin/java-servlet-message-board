package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.exception.NotFoundException;
import com.vladm.demoservlet.exception.UserAlreadyExistsException;
import com.vladm.demoservlet.model.User;

import java.util.*;

public class UserService {

    private static UserService INSTANCE;
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name, String email, String password){
        String id = UUID.randomUUID().toString();

        User user = new User(id, name, email, password);

        if(userDao.userExists(user)){
            throw new UserAlreadyExistsException();
        }

        return userDao.save(user);
    }

    public User findOne(String id) {
        return userDao.findOne(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<User> findByName(String name) {
        return userDao.findByName(name);
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public static UserService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserService(FileStorageUserDao.getInstance());
        }

        return INSTANCE;
    }
}
