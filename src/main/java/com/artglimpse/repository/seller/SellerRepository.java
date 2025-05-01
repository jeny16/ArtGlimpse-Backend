package com.artglimpse.repository.seller;

import com.artglimpse.model.seller.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
    // Additional query methods can be defined here if needed.
}
