package com.artglimpse.seller.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sellerProfiles")
public class SellerProfile {
    @Id
    private String id;
    private String storeName;
    private String contactNumber;
    private String address;

    public SellerProfile() {
    }

    public SellerProfile(String id, String storeName, String contactNumber, String address) {
        this.id = id;
        this.storeName = storeName;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
