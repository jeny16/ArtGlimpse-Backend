package com.artglimpse.buyer.dto.product;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.profile.Address;
import org.bson.types.ObjectId;

public class CreateOrderRequest {
    // Changed from String to ObjectId for dynamic user id assignment
    private ObjectId userId;
    private Cart cart;
    private Address shippingAddress;

    public CreateOrderRequest() {
    }

    // Getters and setters
    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
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
