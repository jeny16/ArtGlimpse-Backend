package com.artglimpse.authentication.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private ObjectId buyerProfile;
    private ObjectId sellerProfile;

    public User() {
    }

    public User(String username, String email, String password, String role, ObjectId buyerProfile,
            ObjectId sellerProfile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.buyerProfile = buyerProfile;
        this.sellerProfile = sellerProfile;
    }

    // Getters and setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ObjectId getBuyerProfile() {
        return buyerProfile;
    }

    public void setBuyerProfile(ObjectId buyerProfile) {
        this.buyerProfile = buyerProfile;
    }

    public ObjectId getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(ObjectId sellerProfile) {
        this.sellerProfile = sellerProfile;
    }
}
