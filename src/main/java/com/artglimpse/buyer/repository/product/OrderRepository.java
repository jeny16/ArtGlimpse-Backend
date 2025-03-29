package com.artglimpse.buyer.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artglimpse.buyer.model.product.Order;
import org.bson.types.ObjectId;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    // Updated to use ObjectId instead of String
    List<Order> findByUserId(ObjectId userId);
}
