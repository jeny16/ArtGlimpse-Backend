package com.artglimpse.dto.product;

public class WishlistRequest {
    private String userId;
    private String productId;

    public WishlistRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
