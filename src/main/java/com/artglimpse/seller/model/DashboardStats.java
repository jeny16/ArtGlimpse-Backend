package com.artglimpse.seller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "stats")
public class DashboardStats {
    @Id
    private String id;
    private BigDecimal totalRevenue;
    private int totalOrders;
    private int productsSold;
    private double conversionRate;
    private int visitors;

    // Constructors
    public DashboardStats() {
        this.totalRevenue = BigDecimal.ZERO;
        this.totalOrders = 0;
        this.productsSold = 0;
        this.conversionRate = 0.0;
        this.visitors = 1000; // Default visitor count for conversion rate
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
    public int getProductsSold() { return productsSold; }
    public void setProductsSold(int productsSold) { this.productsSold = productsSold; }
    public double getConversionRate() { return conversionRate; }
    public void setConversionRate(double conversionRate) { this.conversionRate = conversionRate; }
    public int getVisitors() { return visitors; }
    public void setVisitors(int visitors) { this.visitors = visitors; }
}
