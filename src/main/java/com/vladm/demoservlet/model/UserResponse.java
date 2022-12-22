package com.vladm.demoservlet.model;

import java.util.List;

public class UserResponse {

    private final String id;
    private final String name;
    private final String email;

    private final List<MessageResponse> messageResponses;

    public UserResponse(String id, String name, String email, List<MessageResponse> messageResponses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.messageResponses = messageResponses;
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

    public List<MessageResponse> getMessageResponses() {
        return messageResponses;
    }

    public static UserResponse make(User user, List<MessageResponse> messageResponses) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), messageResponses);
    }
}
