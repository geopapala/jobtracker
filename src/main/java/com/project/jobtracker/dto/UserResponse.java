package com.project.jobtracker.dto;

public class UserResponse {

    private String id;
    private String name;
    private String email;
    private String createdAt;

    public UserResponse() {}

    public UserResponse(String id, String name, String email, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
