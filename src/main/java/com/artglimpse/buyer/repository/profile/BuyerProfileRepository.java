package com.artglimpse.buyer.repository.profile;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.artglimpse.buyer.model.profile.BuyerProfile;

public interface BuyerProfileRepository extends MongoRepository<BuyerProfile, String> {
    // Additional query methods can be defined here if needed.
}
