package com.artglimpse.seller.dto;

public class SellerProfileDTO {
    private String name;
    private String email;
    private String contactNumber;
    private String storeName;

    public SellerProfileDTO() {
    }

    public SellerProfileDTO(String name, String email, String contactNumber, String storeName) {
        this.name = name != null ? name : "NA";
        this.email = email != null ? email : "NA";
        this.contactNumber = contactNumber != null ? contactNumber : "NA";
        this.storeName = storeName != null ? storeName : "NA";
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "NA";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "NA";
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber != null ? contactNumber : "NA";
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName != null ? storeName : "NA";
    }
}
