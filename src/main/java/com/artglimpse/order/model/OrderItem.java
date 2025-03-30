package com.artglimpse.order.model;

import org.bson.types.ObjectId;

import com.artglimpse.product.model.Product;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Transient;

public class OrderItem {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId productId;

    private int quantity;
    private double price; // Price at the time of order

    // Transient field to hold the complete product data
    @Transient
    private Product productData;

    public OrderItem() {
    }

    public OrderItem(ObjectId productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProductData() {
        return productData;
    }

    public void setProductData(Product productData) {
        this.productData = productData;
    }
}
