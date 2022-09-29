package com.vladm.demoservlet.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super("User with this name/email already exists");
    }
}
