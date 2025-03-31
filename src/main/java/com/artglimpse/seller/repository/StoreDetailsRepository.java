package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.StoreDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreDetailsRepository extends MongoRepository<StoreDetails, String> {
}
