package com.artglimpse.buyer.model.product;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.artglimpse.buyer.model.profile.Address;

import java.util.Date;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private ObjectId id;
    private String userId;
    private List<OrderItem> items;
    private double totalAmount;
    private String paymentStatus; // e.g., "PAID"
    private Address shippingAddress;
    private Date createdAt;

    public Order() {}

    public Order(String userId, List<OrderItem> items, double totalAmount, String paymentStatus, Address shippingAddress, Date createdAt) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.shippingAddress = shippingAddress;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
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
}
