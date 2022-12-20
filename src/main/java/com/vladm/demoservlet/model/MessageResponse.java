package com.vladm.demoservlet.model;

import java.util.List;

public class MessageResponse {

    private final String messageId;
    private final String userId;
    private final String text;
    private final String userName;
    private final boolean isReply;
    
    private final MessageResponse replyTo;
    private final List<MessageResponse> replies;


    public MessageResponse(String messageId, String userId, String text, String userName, boolean isReply, MessageResponse replyTo, List<MessageResponse> replies) {
        this.messageId = messageId;
        this.userId = userId;
        this.text = text;
        this.userName = userName;
        this.isReply = isReply;
        this.replyTo = replyTo;
        this.replies = replies;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }

    public List<MessageResponse> getReplies() {
        return replies;
    }

    public boolean isReply() {
        return isReply;
    }

    public MessageResponse getReplyTo() {
        return replyTo;
    }
}
