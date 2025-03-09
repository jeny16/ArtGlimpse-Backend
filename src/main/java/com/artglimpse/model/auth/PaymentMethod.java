package com.artglimpse.model.auth;

public class PaymentMethod {
    private String type;
    private String cardNumber;
    private String expiry;

    public PaymentMethod() {
    }

    public PaymentMethod(String type, String cardNumber, String expiry) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
