package com.artglimpse.buyer.dto.auth;

import java.util.List;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.buyer.model.profile.PaymentMethod;

public class ProfileResponse {
    private String id;
    private String username;
    private String email;
    private String mobile;
    private String gender;
    private String dateOfBirth;
    private String hintName;
    private String alternateMobile;
    private List<Address> addresses;
    private List<PaymentMethod> paymentMethods;

    public ProfileResponse() {
    }

    public ProfileResponse(String id, String username, String email, String mobile, String gender,
            String dateOfBirth, String hintName, String alternateMobile,
            List<Address> addresses, List<PaymentMethod> paymentMethods) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.hintName = hintName;
        this.alternateMobile = alternateMobile;
        this.addresses = addresses;
        this.paymentMethods = paymentMethods;
    }

    // Getters and setters...
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
