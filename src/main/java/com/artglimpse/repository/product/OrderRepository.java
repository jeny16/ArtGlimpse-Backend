package com.artglimpse.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artglimpse.model.product.Order;
import org.bson.types.ObjectId;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    List<Order> findByUserId(String userId);
}
