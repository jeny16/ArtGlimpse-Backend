package com.artglimpse.authentication.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.artglimpse.buyer.model.profile.BuyerProfile;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private String role; // e.g., ROLE_USER, ROLE_SELLER, ROLE_ADMIN

    @DBRef(lazy = true)
    private BuyerProfile buyerProfile; // Reference to the buyer profile (only for ROLE_USER)

    public User() {
    }

    public User(String username, String email, String password, String role, BuyerProfile buyerProfile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.buyerProfile = buyerProfile;
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

    public BuyerProfile getBuyerProfile() {
        return buyerProfile;
    }

    public void setBuyerProfile(BuyerProfile buyerProfile) {
        this.buyerProfile = buyerProfile;
    }
}
