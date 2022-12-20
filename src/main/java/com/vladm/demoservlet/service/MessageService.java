package com.vladm.demoservlet.service;

import com.vladm.demoservlet.dao.FSMessageDao;
import com.vladm.demoservlet.dao.FileStorageUserDao;
import com.vladm.demoservlet.dao.MessageDao;
import com.vladm.demoservlet.dao.UserDao;
import com.vladm.demoservlet.exception.ClientException;
import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class MessageService {

    private final UserDao userDao;
    private final MessageDao messageDao;

    private static MessageService INSTANCE;

    public MessageService(UserDao userDao, MessageDao messageDao) {
        this.userDao = userDao;
        this.messageDao = messageDao;
    }

    public Optional<Message> publishMessage(String userId, String text, Optional<String> replyToId) {
        return userDao.findOne(userId)
                .map(usr -> {
                    String id = UUID.randomUUID().toString();
                    Message msg = new Message(id, text, userId, replyToId.isPresent(), replyToId.orElse(null));
                    messageDao.save(msg);
                    usr.addMessage(msg.getId());
                    userDao.update(usr);

                    replyToId.ifPresent(msgId -> messageDao.findOne(msgId)
                            .ifPresent(persistedMsg -> {
                                persistedMsg.addReply(id);
                                messageDao.update(persistedMsg);
                            })
                    );

                    return msg;
                });
    }

    public Message findOne(String id) {
        return messageDao.findOne(id)
                .orElseThrow(() -> new ClientException("Message not found", 404));
    }

    public List<Message> findAll(String userId) {
        return userDao.findOne(userId)
                .map(usr -> messageDao.findByUserId(userId))
                .orElse(new ArrayList<>());
    }

    public List<Message> findAllReplies(String messageId) {
        return messageDao.findOne(messageId)
                .map(Message::getReplies)
                .orElse(new ArrayList<>())
                .stream()
                .map(messageDao::findOne)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public MessageResponse transformToMessageResponse(Message message) {
        final var messageReplies =  this.findAllReplies(message.getId());

        final var replyTo = Optional.ofNullable(message.getReplyId())
                .map(id -> {
                    final var msg = this.findOne(id);
                    final var usr = userDao.findOne(msg.getUserId()).orElse(User.DEFAULT);

                    return new MessageResponse(msg.getId(), usr.getId(), msg.getText(), usr.getName(), false, null, Collections.emptyList());
                })
                .orElse(null);

        final var user = userDao.findOne(message.getUserId()).orElse(User.DEFAULT);
        final var repliesWithUserInfo = messageReplies.stream()
                .map(msg -> {
                    final var usr = userDao.findOne(message.getUserId()).orElse(User.DEFAULT);

                    // yea, it's reply, but it's inside replyList, so no need to mark as reply
                    return new MessageResponse(msg.getId(), usr.getId(), msg.getText(), usr.getName(), false, null, Collections.emptyList());
                })
                .collect(Collectors.toList());

        return new MessageResponse(message.getId(), user.getId(), message.getText(), user.getName(), message.isReply(), replyTo, repliesWithUserInfo);
    }
    public static MessageService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MessageService(FileStorageUserDao.getInstance(), FSMessageDao.getInstance());
        }

        return INSTANCE;
    }
}
