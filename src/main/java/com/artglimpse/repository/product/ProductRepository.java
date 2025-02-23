package com.artglimpse.repository.product;

import com.artglimpse.model.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional query methods can be defined here for filterization in the future.
}