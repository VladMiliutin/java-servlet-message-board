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
    void delete(String id);
    User update(User user);

}
