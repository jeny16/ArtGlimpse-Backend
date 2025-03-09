package com.artglimpse.authentication.dto;

public class JwtResponse {
    private String token;
    private String userId; // add this field

    public JwtResponse(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }
    // getters and setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
