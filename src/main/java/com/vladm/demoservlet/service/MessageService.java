package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.model.Message;

import java.util.UUID;

public class MessageService {

    private final UserDao userDao;

    private static MessageService INSTANCE;

    public MessageService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Message publishMessage(String userId, String text) {
        return userDao.findOne(userId)
                .map(usr -> {
                    String id = UUID.randomUUID().toString();
                    Message msg = new Message(id, text, userId);
                    usr.addMessage(msg);
                    userDao.update(usr);
                    return msg;
                })
                .orElse(null);
    }

    public static MessageService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MessageService(FileStorageUserDao.getInstance());
        }

        return INSTANCE;
    }
}
