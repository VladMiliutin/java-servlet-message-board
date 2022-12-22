package com.vladm.demoservlet.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UserExistsException extends ClientException {

    public UserExistsException() {
        super(HttpServletResponse.SC_BAD_REQUEST, "User with this name/email already exists");
    }
}
