package com.artglimpse.order.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.buyer.dto.auth.ProfileResponse; // Merged profile DTO
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @org.springframework.data.annotation.Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    // This field is used for storage only
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;

    private List<OrderItem> items;
    private double totalAmount;
    private String paymentStatus;
    private String orderStatus;
    private Address shippingAddress;
    private Date createdAt;

    // Transient field to hold full user data in the response
    @Transient
    private ProfileResponse userData;

    public Order() {
    }

    public Order(ObjectId userId, List<OrderItem> items, double totalAmount, String paymentStatus,
            Address shippingAddress, Date createdAt) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
        // Default orderStatus: if payment is done, we start with "New"
        this.orderStatus = "New";
    }

    // Getters and setters

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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ProfileResponse getUserData() {
        return userData;
    }

    public void setUserData(ProfileResponse userData) {
        this.userData = userData;
    }
}
