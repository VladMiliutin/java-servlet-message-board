package com.vladm.demoservlet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {

    private String id;
    private String text;
    private String userId;

    private boolean isReply;

    private List<String> replies = new ArrayList<>();

    private String replyId;

    public Message(String id, String text, String userId, boolean isReply, String replyId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.isReply = isReply;
        this.replyId = replyId;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }


    public void addReply(String messageId) {
        this.replies.add(messageId);
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(text, message.text) && Objects.equals(userId, message.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, userId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
