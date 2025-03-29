package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.SellerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SellerProfileRepository extends MongoRepository<SellerProfile, String> {
}
