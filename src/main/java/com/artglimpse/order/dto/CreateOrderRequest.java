package com.artglimpse.order.dto;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.profile.Address;
import org.bson.types.ObjectId;

public class CreateOrderRequest {
    private ObjectId userId;
    private Cart cart;
    private Address shippingAddress;

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
