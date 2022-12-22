package com.vladm.demoservlet.exception;

public class ClientException extends RuntimeException {

    public final int statusCode;

    public ClientException(int statusCode) {
        this(statusCode, "Invalid request");
    }

    public ClientException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
