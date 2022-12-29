package com.vladm.demoservlet.service;

import com.vladm.demoservlet.model.Message;
import com.vladm.demoservlet.model.MessageResponse;
import com.vladm.demoservlet.model.User;
import com.vladm.demoservlet.model.UserResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserMessageService {

    private final UserService userService;
    private final MessageService messageService;

    private static UserMessageService INSTANCE;

    public UserMessageService(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    public UserResponse findUser(String userId) {
        final User user = userService.findOne(userId);
        List<MessageResponse> allMessagesByUserId = this.findAllMessagesByUserId(userId);

        return UserResponse.make(user, allMessagesByUserId);
    }

    public MessageResponse findMessageById(String messageId) {
        Message message = messageService.findOne(messageId);
        return transformToMessageResponse(message);
    }

    public List<MessageResponse> findAllMessagesByUserId(String userId) {
        List<Message> messages = messageService.findAll(userId);

        return messages.stream()
                .map(this::transformToMessageResponse)
                .collect(Collectors.toList());
    }

    private MessageResponse transformToMessageResponse(Message message) {
        List<Message> replies = messageService.findAllReplies(message.getId());

        final MessageResponse replyTo = Optional.ofNullable(message.getReplyToId())
                .map(id -> {
                    Message reply = messageService.findOne(id);
                    User user = userService.findOne(reply.getUserId());

                    return MessageResponse.make(reply, user);
                }).orElse(null);

        User user = userService.findOne(message.getUserId());
        List<MessageResponse> repliesWithUserInfo = replies.stream()
                .map(msg -> {
                    User usr = userService.findOne(msg.getUserId());

                    return MessageResponse.make(msg, usr) ;
                })
                .collect(Collectors.toList());

        return MessageResponse.make(message, user, replyTo, repliesWithUserInfo);
    }



    public static UserMessageService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserMessageService(UserService.getInstance(), MessageService.getInstance());
        }

        return INSTANCE;
    }
}
