package com.artglimpse.buyer.model.profile;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "buyerProfile")
public class BuyerProfile {
    @Id
    private String id; // same as the common user id
    private String mobile;
    private String gender;
    private String dateOfBirth;
    private String hintName;
    private String alternateMobile;
    private List<Address> addresses = new ArrayList<>();
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    public BuyerProfile() {
    }

    public BuyerProfile(String id, String mobile, String gender, String dateOfBirth,
            String hintName, String alternateMobile,
            List<Address> addresses, List<PaymentMethod> paymentMethods) {
        this.id = id;
        this.mobile = mobile;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.hintName = hintName;
        this.alternateMobile = alternateMobile;
        this.addresses = addresses;
        this.paymentMethods = paymentMethods;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHintName() {
        return hintName;
    }

    public void setHintName(String hintName) {
        this.hintName = hintName;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
