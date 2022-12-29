package com.vladm.demoservlet.dao;

import com.vladm.demoservlet.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User save(User user);
    List<User> findAll();
    Optional<User> findOne(String id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    User update(User user);
    void delete(String id);

    boolean userExists(User user);

}
