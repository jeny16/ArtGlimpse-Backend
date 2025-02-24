package com.artglimpse.repository.product;

import com.artglimpse.model.product.Wishlist;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, ObjectId> {
    Optional<Wishlist> findByUserId(ObjectId userId);
}
