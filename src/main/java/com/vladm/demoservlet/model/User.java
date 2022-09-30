package com.vladm.demoservlet.model;

import java.util.Objects;

public class User {

    private String id;
    private String name;
    private String email;

    public User(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User() {

    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
