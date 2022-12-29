package com.vladm.demoservlet.exception;

public class ClientException extends RuntimeException {

    public final int statusCode;

    public ClientException(int statusCode) {
        super("Invalid request");
        this.statusCode = statusCode;
    }

    public ClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
