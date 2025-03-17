package com.artglimpse.seller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "products")
public class AddProduct {

    @Id
    private String id;
    
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private Integer stock;
    private String category;
    private Boolean discount;
    private Integer percentageDiscount;
    private LocalDate validUntilDiscount;
    private String processingTime;
    private String shippingTime;
    private BigDecimal shippingCost;
    private LocalDate estimatedDelivery;
    private String materialsMade;
    private String tags;
    private List<String> images;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Boolean getDiscount() {
        return discount;
    }
    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }
    public Integer getPercentageDiscount() {
        return percentageDiscount;
    }
    public void setPercentageDiscount(Integer percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }
    public LocalDate getValidUntilDiscount() {
        return validUntilDiscount;
    }
    public void setValidUntilDiscount(LocalDate validUntilDiscount) {
        this.validUntilDiscount = validUntilDiscount;
    }
    public String getProcessingTime() {
        return processingTime;
    }
    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }
    public String getShippingTime() {
        return shippingTime;
    }
    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }
    public BigDecimal getShippingCost() {
        return shippingCost;
    }
    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }
    public LocalDate getEstimatedDelivery() {
        return estimatedDelivery;
    }
    public void setEstimatedDelivery(LocalDate estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }
    public String getMaterialsMade() {
        return materialsMade;
    }
    public void setMaterialsMade(String materialsMade) {
        this.materialsMade = materialsMade;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
}
