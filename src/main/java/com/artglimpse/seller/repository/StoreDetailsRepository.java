package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.StoreDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDetailsRepository extends MongoRepository<StoreDetails, String> {
}
