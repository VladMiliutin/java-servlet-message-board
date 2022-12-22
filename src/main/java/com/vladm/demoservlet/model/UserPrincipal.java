package com.vladm.demoservlet.model;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private final String userId;
    private final String username;

    private final String password;

    public UserPrincipal(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
