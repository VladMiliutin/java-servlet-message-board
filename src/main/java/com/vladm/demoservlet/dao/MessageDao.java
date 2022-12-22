package com.vladm.demoservlet.dao;

import com.vladm.demoservlet.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {

    Message save(Message message);
    List<Message> findAll();
    Optional<Message> findOne(String id);
    List<Message> findByUserId(String userId);
    List<Message> findAllByIds(List<String> ids);
    void delete(String id);
    Message update(Message message);

}
