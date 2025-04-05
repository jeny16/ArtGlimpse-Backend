package com.artglimpse.product.repository;
 
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.artglimpse.product.model.Product;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findAllById(Iterable<String> ids);
    
    // Updated method: find by seller as a String
    List<Product> findBySeller(ObjectId seller);
}
