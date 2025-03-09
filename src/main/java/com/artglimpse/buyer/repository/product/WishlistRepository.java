package com.artglimpse.buyer.repository.product;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.artglimpse.buyer.model.product.Wishlist;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, ObjectId> {
    Optional<Wishlist> findByUserId(ObjectId userId);
}
