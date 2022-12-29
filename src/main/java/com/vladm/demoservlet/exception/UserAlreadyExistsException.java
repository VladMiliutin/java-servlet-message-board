package com.vladm.demoservlet.exception;

import jakarta.servlet.http.HttpServletResponse;

public class UserAlreadyExistsException extends ClientException{

    public UserAlreadyExistsException() {
        super("User with this email/name already exists", HttpServletResponse.SC_BAD_REQUEST);
    }
}
