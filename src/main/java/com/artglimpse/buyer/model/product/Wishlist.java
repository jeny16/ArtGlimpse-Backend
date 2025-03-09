package com.artglimpse.buyer.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private List<ObjectId> products = new ArrayList<>();
}
