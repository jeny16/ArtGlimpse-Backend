package com.artglimpse.buyer.dto.product;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.profile.Address;

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
