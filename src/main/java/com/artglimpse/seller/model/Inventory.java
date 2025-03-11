package com.artglimpse.seller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;
    private String name;
    private String category;
    private int stock;
    private double price;  // Ensure this field exists
    private String status; // Ensure this field exists (for status like 'available', 'sold out', etc.)

    // Default constructor
    public Inventory() {}

    // Constructor with all fields
    public Inventory(String name, String category, int stock, double price, String status) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
