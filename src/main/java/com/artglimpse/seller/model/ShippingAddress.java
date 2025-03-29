package com.artglimpse.seller.model;

public class ShippingAddress {
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String mobile;
    private boolean isDefault;
    private String addressType;

    // Default constructor
    public ShippingAddress() {
    }

    // Parameterized constructor
    public ShippingAddress(String name, String street, String city, String state, String zip, String country, String mobile, boolean isDefault, String addressType) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.mobile = mobile;
        this.isDefault = isDefault;
        this.addressType = addressType;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }
}
