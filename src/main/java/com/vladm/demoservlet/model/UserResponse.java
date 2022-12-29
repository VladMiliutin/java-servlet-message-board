package com.vladm.demoservlet.model;

import java.util.List;

public class UserResponse {

    private final String id;
    private final String name;
    private final String email;

    private final List<MessageResponse> messageList;

    public UserResponse(String id, String name, String email, List<MessageResponse> messageList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.messageList = messageList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<MessageResponse> getMessageList() {
        return messageList;
    }

    public static UserResponse make(User user, List<MessageResponse> messageList) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), messageList);
    }
}
