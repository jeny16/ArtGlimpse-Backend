package com.artglimpse.buyer.model.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "cart")
public class Cart {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;

    private List<CartItem> items = new ArrayList<>();

    private Double donationAmount; // May be null
    private String couponCode; // May be null

    // Use Spring Data's @Transient annotation to ensure this field is not persisted
    @Transient
    private BuyerProfile userData;

    public Cart() {
    }

    public Cart(ObjectId id, ObjectId userId, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BuyerProfile getUserData() {
        return userData;
    }

    public void setUserData(BuyerProfile userData) {
        this.userData = userData;
    }

    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
