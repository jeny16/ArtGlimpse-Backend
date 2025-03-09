package com.artglimpse.buyer.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.artglimpse.buyer.model.product.Product;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllById(Iterable<String> ids);
}