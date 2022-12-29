package com.vladm.demoservlet.model;

import java.util.Collections;
import java.util.List;

public class MessageResponse {

    private final String id;
    private final String text;
    private final String userId;
    private final String userName;

    private final boolean isReply;
    private final MessageResponse replyTo;
    private final List<MessageResponse> replies;

    public MessageResponse(String id, String text, String userId, String userName, boolean isReply, MessageResponse replyTo, List<MessageResponse> replies) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.userName = userName;
        this.isReply = isReply;
        this.replyTo = replyTo;
        this.replies = replies;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isReply() {
        return isReply;
    }

    public MessageResponse getReplyTo() {
        return replyTo;
    }

    public List<MessageResponse> getReplies() {
        return replies;
    }

    public static MessageResponse make(Message message, User user, MessageResponse replyTo, List<MessageResponse> replies) {
        return new MessageResponse(message.getId(), message.getText(), message.getUserId(),
                user.getName(), message.isReply(), replyTo, replies);
    }

    public static MessageResponse make(Message message, User user) {
       return make(message, user, null, Collections.emptyList());
    }
}
