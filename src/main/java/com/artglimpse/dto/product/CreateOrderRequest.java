package com.artglimpse.dto.product;

import com.artglimpse.model.auth.Address;
import com.artglimpse.model.product.Cart;

public class CreateOrderRequest {
    private String userId;
    private Cart cart;
    private Address shippingAddress;

    public CreateOrderRequest() {
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
