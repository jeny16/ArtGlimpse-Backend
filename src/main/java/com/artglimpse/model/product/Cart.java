// package com.artglimpse.model.product;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.bson.types.ObjectId;
// import com.fasterxml.jackson.databind.annotation.JsonSerialize;
// import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
// import java.util.ArrayList;
// import java.util.List;
// import com.artglimpse.model.auth.User; // adjust package as needed

// @Document(collection = "cart")
// public class Cart {

//     @Id
//     @JsonSerialize(using = ToStringSerializer.class)
//     private ObjectId id;

//     @JsonSerialize(using = ToStringSerializer.class)
//     private ObjectId userId;

//     private List<CartItem> items = new ArrayList<>();

//     // Transient field to hold full user data (populated in service)
//     private transient User userData;

//     public Cart() {
//     }

//     public Cart(ObjectId id, ObjectId userId, List<CartItem> items) {
//         this.id = id;
//         this.userId = userId;
//         this.items = items;
//     }

//     // Getters and Setters
//     public ObjectId getId() {
//         return id;
//     }

//     public void setId(ObjectId id) {
//         this.id = id;
//     }

//     public ObjectId getUserId() {
//         return userId;
//     }

//     public void setUserId(ObjectId userId) {
//         this.userId = userId;
//     }

//     public List<CartItem> getItems() {
//         return items;
//     }

//     public void setItems(List<CartItem> items) {
//         this.items = items;
//     }

//     public User getUserData() {
//         return userData;
//     }

//     public void setUserData(User userData) {
//         this.userData = userData;
//     }
// }

// File: src/main/java/com/artglimpse/model/product/Cart.java
package com.artglimpse.model.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.ArrayList;
import java.util.List;
import com.artglimpse.model.auth.User; // adjust package as needed

@Document(collection = "cart")
public class Cart {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;

    private List<CartItem> items = new ArrayList<>();

    // New fields
    private Double donationAmount; // May be null
    private String couponCode; // May be null

    // Transient field to hold full user data (populated in service)
    private transient User userData;

    public Cart() {
    }

    public Cart(ObjectId id, ObjectId userId, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    // New getters and setters
    public Double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
