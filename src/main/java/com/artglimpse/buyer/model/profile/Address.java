package com.artglimpse.buyer.model.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import java.util.UUID;

public class Address {
    @Id
    private String id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String mobile;

    @JsonProperty("isDefault")
    private boolean isDefault;

    private String addressType;

    public Address() {
        this.id = UUID.randomUUID().toString();
    }

    public Address(String id, String name, String street, String city, String state, String zip, String country,
            boolean isDefault,
            String addressType, String mobile) {
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.isDefault = isDefault;
        this.addressType = addressType;
        this.mobile = mobile;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        // If a null value is provided, generate a new UUID.
        this.id = (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id;
    }

    // (Other getters and setters remain unchanged)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return isDefault;
    }

    @JsonProperty("isDefault")
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
