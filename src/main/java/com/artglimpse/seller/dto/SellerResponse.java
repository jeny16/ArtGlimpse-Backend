package com.artglimpse.seller.dto;

import com.artglimpse.authentication.model.User;
import com.artglimpse.seller.model.SellerProfile;

public class SellerResponse {
    private String id;
    private String username;
    private String email;
    private String role;
    private SellerProfile sellerProfile; // Full seller profile details

    public SellerResponse(User user, SellerProfile sellerProfile) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.sellerProfile = sellerProfile;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public SellerProfile getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(SellerProfile sellerProfile) {
        this.sellerProfile = sellerProfile;
    }
}
