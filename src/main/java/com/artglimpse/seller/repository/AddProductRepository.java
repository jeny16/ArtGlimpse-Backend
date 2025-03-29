package com.artglimpse.seller.repository;

import com.artglimpse.seller.model.AddProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddProductRepository extends MongoRepository<AddProduct, String> {
}