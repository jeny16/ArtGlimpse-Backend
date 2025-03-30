package com.artglimpse.seller.dto;

import java.util.List;
import com.artglimpse.seller.model.ShippingAddress;

public class SellerProfileResponse {
    private String id;
    private String username;
    private String email;
    private String contactNumber;
    private String businessName;
    private List<ShippingAddress> storeAddress;

    public SellerProfileResponse() {
    }

    public SellerProfileResponse(String id, String username, String email,
                                 String contactNumber, String businessName,
                                 List<ShippingAddress> storeAddress) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.contactNumber = contactNumber;
        this.businessName = businessName;
        this.storeAddress = storeAddress;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public List<ShippingAddress> getStoreAddress() { return storeAddress; }
    public void setStoreAddress(List<ShippingAddress> storeAddress) { this.storeAddress = storeAddress; }
}
