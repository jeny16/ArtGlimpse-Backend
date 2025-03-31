package com.artglimpse.seller.dto;

public class SellerProfileDTO {
    private String name;
    private String email;
    private String contactNumber;
    private String address;

    public SellerProfileDTO() {
    }

    public SellerProfileDTO(String name, String email, String contactNumber, String address) {
        this.name = name != null ? name : "Not Provided";
        this.email = email != null ? email : "Not Provided";
        this.contactNumber = contactNumber != null ? contactNumber : "Not Provided";
        this.address = address != null ? address : "Not Provided";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "Not Provided";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "Not Provided";
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber != null ? contactNumber : "Not Provided";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address != null ? address : "Not Provided";
    }

    @Override
    public String toString() {
        return "SellerProfileDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
