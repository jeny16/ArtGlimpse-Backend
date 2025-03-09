package com.artglimpse.buyer.dto.auth;

import java.util.List;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.buyer.model.profile.PaymentMethod;

public class UserProfileUpdateRequest {
    private List<Address> addresses;
    private String mobile;
    private List<PaymentMethod> paymentMethods;
    private String gender;
    private String dateOfBirth;
    private String hintName;
    private String alternateMobile;

    public UserProfileUpdateRequest() {
    }

    // Existing getters and setters
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
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
}
