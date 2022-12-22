package com.vladm.demoservlet.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotFoundException extends ClientException{

    public NotFoundException() {
        this("Not found");
    }

    public NotFoundException(String message) {
        super(HttpServletResponse.SC_NOT_FOUND, message);
    }
}
