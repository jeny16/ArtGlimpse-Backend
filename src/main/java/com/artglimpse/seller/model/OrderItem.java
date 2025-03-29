package com.artglimpse.seller.model;

import org.bson.types.ObjectId;

public class OrderItem {
    private ObjectId productId;
    private int quantity;
    private double price;

    // Getters and Setters
    public ObjectId getProductId() { return productId; }
    public void setProductId(ObjectId productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
