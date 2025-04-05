package com.artglimpse.product.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String categories;
    private String name;
    private String description;
    private double price;
    private String currency;
    private int stock;
    private List<String> images;
    private boolean discount;
    private LocalDate valid_Until_Discount;
    private int percentage_Discount;
    // Changed seller from ObjectId to String
    private ObjectId seller;
    private String processing_Time;
    private String shipping_Time;
    private double shipping_Cost;
    private String estimated_Delivery;
    private List<String> countries_Available;
    private List<String> materials_Made;
    private List<String> tags;

    public Product() {
    }

    public Product(String categories, String name, String description, double price, String currency, int stock,
            List<String> images, boolean discount, LocalDate valid_Until_Discount, int percentage_Discount,
            ObjectId seller, String processing_Time, String shipping_Time, double shipping_Cost,
            String estimated_Delivery, List<String> countries_Available, List<String> materials_Made,
            List<String> tags) {
        this.categories = categories;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.images = images;
        this.discount = discount;
        this.valid_Until_Discount = valid_Until_Discount;
        this.percentage_Discount = percentage_Discount;
        this.seller = seller;
        this.processing_Time = processing_Time;
        this.shipping_Time = shipping_Time;
        this.shipping_Cost = shipping_Cost;
        this.estimated_Delivery = estimated_Delivery;
        this.countries_Available = countries_Available;
        this.materials_Made = materials_Made;
        this.tags = tags;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
       this.id = id;
    }

    public String getCategories() {
       return categories;
    }

    public void setCategories(String categories) {
       this.categories = categories;
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

    public double getPrice() {
       return price;
    }

    public void setPrice(double price) {
       this.price = price;
    }

    public String getCurrency() {
       return currency;
    }

    public void setCurrency(String currency) {
       this.currency = currency;
    }

    public int getStock() {
       return stock;
    }

    public void setStock(int stock) {
       this.stock = stock;
    }

    public List<String> getImages() {
       return images;
    }

    public void setImages(List<String> images) {
       this.images = images;
    }

    public boolean isDiscount() {
       return discount;
    }

    public void setDiscount(boolean discount) {
       this.discount = discount;
    }

    public LocalDate getValid_Until_Discount() {
       return valid_Until_Discount;
    }

    public void setValid_Until_Discount(LocalDate valid_Until_Discount) {
       this.valid_Until_Discount = valid_Until_Discount;
    }

    public int getPercentage_Discount() {
       return percentage_Discount;
    }

    public void setPercentage_Discount(int percentage_Discount) {
       this.percentage_Discount = percentage_Discount;
    }

    public ObjectId getSeller() {
       return seller;
    }

    public void setSeller(ObjectId seller) {
       this.seller = seller;
    }

    public String getProcessing_Time() {
       return processing_Time;
    }

    public void setProcessing_Time(String processing_Time) {
       this.processing_Time = processing_Time;
    }

    public String getShipping_Time() {
       return shipping_Time;
    }

    public void setShipping_Time(String shipping_Time) {
       this.shipping_Time = shipping_Time;
    }

    public double getShipping_Cost() {
       return shipping_Cost;
    }

    public void setShipping_Cost(double shipping_Cost) {
       this.shipping_Cost = shipping_Cost;
    }

    public String getEstimated_Delivery() {
       return estimated_Delivery;
    }

    public void setEstimated_Delivery(String estimated_Delivery) {
       this.estimated_Delivery = estimated_Delivery;
    }

    public List<String> getCountries_Available() {
       return countries_Available;
    }

    public void setCountries_Available(List<String> countries_Available) {
       this.countries_Available = countries_Available;
    }

    public List<String> getMaterials_Made() {
       return materials_Made;
    }

    public void setMaterials_Made(List<String> materials_Made) {
       this.materials_Made = materials_Made;
    }

    public List<String> getTags() {
       return tags;
    }

    public void setTags(List<String> tags) {
       this.tags = tags;
    }
}
