package com.vladm.demoservlet.service;

import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Helps to create User/MessageResponse
 */
public class UserMessageService {

    private final UserService userService;
    private final MessageService messageService;

    private static UserMessageService INSTANCE;

    public UserMessageService(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    public UserResponse findUser(String userId) {
        final var user = userService.findOne(userId);
        final var messages = this.findMessagesByUserId(userId);

        return UserResponse.make(user, messages);
    }

    public MessageResponse findMessageById(String id) {
        return transformToMessageResponse(messageService.findOne(id));
    }

    public List<MessageResponse> findMessagesByUserId(String userId) {
        return messageService.findAll(userId).stream()
                .map(this::transformToMessageResponse)
                .collect(Collectors.toList());
    }

    private MessageResponse transformToMessageResponse(Message message) {
        final var messageReplies =  messageService.findAllReplies(message.getId());

        final var replyTo = Optional.ofNullable(message.getReplyId())
                .map(id -> {
                    final var msg = messageService.findOne(id);
                    final var usr = userService.findOne(msg.getUserId());

                    return MessageResponse.make(msg, usr);
                })
                .orElse(null);

        final var user = userService.findOne(message.getUserId());
        final var repliesWithUserInfo = messageReplies.stream()
                .map(msg -> {
                    final var usr = userService.findOne(msg.getUserId());
                    // yea, it's reply, but it's inside replyList, so no need to mark as reply
                    return MessageResponse.make(msg, usr);
                })
                .collect(Collectors.toList());

        return MessageResponse.make(message, user, replyTo, repliesWithUserInfo);
    }

    public static UserMessageService getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserMessageService(UserService.getInstance(), MessageService.getInstance());
        }

        return INSTANCE;
    }
}
