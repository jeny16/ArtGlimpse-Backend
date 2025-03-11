package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    // Custom query methods (if needed) can be defined here
}

