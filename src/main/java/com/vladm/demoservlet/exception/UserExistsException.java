package com.vladm.demoservlet.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UserExistsException extends ClientException {

    public UserExistsException() {
        super("User with this name/email already exists", HttpServletResponse.SC_BAD_REQUEST);
    }
}
