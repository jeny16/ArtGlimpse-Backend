// package com.artglimpse.model.product;

// import org.bson.types.ObjectId;
// import com.fasterxml.jackson.databind.annotation.JsonSerialize;
// import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
// import com.artglimpse.model.product.Product; // adjust package as needed

// public class CartItem {

//     @JsonSerialize(using = ToStringSerializer.class)
//     private ObjectId productId;

//     private int quantity;

//     // Transient field to hold full product data (populated in service)
//     private transient Product productData;

//     public CartItem() {
//     }

//     public CartItem(ObjectId productId, int quantity) {
//         this.productId = productId;
//         this.quantity = quantity;
//     }

//     // Getters and Setters
//     public ObjectId getProductId() {
//         return productId;
//     }

//     public void setProductId(ObjectId productId) {
//         this.productId = productId;
//     }

//     public int getQuantity() {
//         return quantity;
//     }

//     public void setQuantity(int quantity) {
//         this.quantity = quantity;
//     }

//     public Product getProductData() {
//         return productData;
//     }

//     public void setProductData(Product productData) {
//         this.productData = productData;
//     }
// }

// File: src/main/java/com/artglimpse/model/product/CartItem.java
package com.artglimpse.model.product;

import org.bson.types.ObjectId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.artglimpse.model.product.Product; // adjust package as needed

public class CartItem {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId productId;

    private int quantity;

    // New fields for discount and shipping cost
    private Double discountPercent; // may be null
    private Double shippingCost; // may be null

    // Transient field to hold full product data (populated in service)
    private transient Product productData;

    public CartItem() {
    }

    public CartItem(ObjectId productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProductData() {
        return productData;
    }

    public void setProductData(Product productData) {
        this.productData = productData;
    }

    // New getters and setters
    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }
}
