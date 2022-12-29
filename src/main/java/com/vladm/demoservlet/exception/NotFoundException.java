package com.vladm.demoservlet.exception;

import jakarta.servlet.http.HttpServletResponse;

public class NotFoundException extends ClientException {
    public NotFoundException() {
        super("Not Found", HttpServletResponse.SC_NOT_FOUND);
    }
}
