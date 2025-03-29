package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.OrdersList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerOrderRepository extends MongoRepository<OrdersList, ObjectId> {
    // Finds orders by sellerId (stored as a String in the document) and orders them by createdAt descending.
    Page<OrdersList> findBySellerIdOrderByCreatedAtDesc(String sellerId, Pageable pageable);
}
