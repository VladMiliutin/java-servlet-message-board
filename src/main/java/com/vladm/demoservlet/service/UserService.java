package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.exception.UserExistsException;
import com.vladm.demoservlet.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private static UserService INSTANCE;
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name, String email, String password) {
        if(userExists(name, email)){
           throw new UserExistsException();
        }

        final String id = UUID.randomUUID().toString();
        User user = new User(id, name, email, password);
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

    public static UserService getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new UserService(FileStorageUserDao.getInstance());
        }

        return INSTANCE;
    }
}
