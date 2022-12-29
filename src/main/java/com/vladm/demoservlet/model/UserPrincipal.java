package com.vladm.demoservlet.model;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private String id;
    private String name;
    private String password;

    public UserPrincipal(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return name;
    }
}
