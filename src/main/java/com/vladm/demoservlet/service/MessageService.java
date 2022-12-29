package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FSMessageDao;
import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.MessageDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.exception.NotFoundException;
import com.vladm.demoservlet.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageService {

    private final UserDao userDao;
    private final MessageDao messageDao;

    private static MessageService INSTANCE;

    public MessageService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
        this.messageDao = messageDao;
    }


    public Message findOne(String id) {
        return messageDao.findOne(id)
                .orElseThrow(NotFoundException::new);
    }
    public List<Message> findAll(String userId) {
        return messageDao.findByUserId(userId);
    }

    public Message save(String userId, String text, Optional<String> replyToId) {
        return userDao.findOne(userId)
                .map(usr -> {
                    String id = UUID.randomUUID().toString();
                    Message msg = new Message(id, text, userId, replyToId.isPresent(), replyToId.orElse(null));
                    messageDao.save(msg);
                    usr.addMessage(msg.getId());
                    userDao.update(usr);

                    replyToId.flatMap(messageDao::findOne).ifPresent(persistedMsg -> {
                        persistedMsg.addReplyId(id);
                        messageDao.update(persistedMsg);
                    });

                    return msg;
                })
                .orElseThrow(NotFoundException::new);
    }

    public List<Message> findAllReplies(String messageId) {
        return messageDao.findOne(messageId)
                .map(Message::getReplyIds)
                .orElse(new ArrayList<>())
                .stream()
                .map(messageDao::findOne)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public static MessageService getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new MessageService(FileStorageUserDao.getInstance(), FSMessageDao.getInstance());
        }

        return INSTANCE;
    }
}
