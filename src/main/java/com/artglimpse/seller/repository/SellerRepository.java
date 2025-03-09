package com.artglimpse.seller.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.artglimpse.seller.model.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
    // Additional query methods can be defined here if needed.
}
