package com.artglimpse.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artglimpse.model.product.Cart;
import org.bson.types.ObjectId;
import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, ObjectId> {
    Optional<Cart> findByUserId(ObjectId userId);
}
