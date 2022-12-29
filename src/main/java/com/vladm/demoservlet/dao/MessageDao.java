package com.vladm.demoservlet.dao;

import com.vladm.demoservlet.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {

    Message save(Message message);
    List<Message> findAll();
    Optional<Message> findOne(String id);
    List<Message> findByUserId(String id);
    List<Message> findByIds(List<String> ids);
    Message update(Message message);
    void delete(String id);

}
