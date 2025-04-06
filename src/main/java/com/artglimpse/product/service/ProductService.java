
package com.artglimpse.product.service;

import com.artglimpse.product.dto.ProductResponse;
import com.artglimpse.product.model.Product;
import com.artglimpse.product.repository.ProductRepository;
import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.authentication.model.User;
import com.artglimpse.seller.repository.SellerProfileRepository;
import com.artglimpse.seller.dto.SellerResponse;
import com.artglimpse.seller.model.SellerProfile;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerProfileRepository sellerProfileRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product, String sellerId) {
        if (!ObjectId.isValid(sellerId)) {
            throw new IllegalArgumentException("Invalid sellerId format: " + sellerId);
        }
        ObjectId sellerObjectId = new ObjectId(sellerId);
        if (!userRepository.existsById(sellerObjectId.toHexString())) {
            throw new RuntimeException("Seller not found with id " + sellerId);
        }
        product.setSeller(sellerObjectId);
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Map<String, Object> updates, String sellerId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, product, value);
            }
        });

        if (sellerId != null && !sellerId.isEmpty()) {
            if (!ObjectId.isValid(sellerId)) {
                throw new IllegalArgumentException("Invalid sellerId format: " + sellerId);
            }
            ObjectId sellerObjectId = new ObjectId(sellerId);
            if (!userRepository.existsById(sellerObjectId.toHexString())) {
                throw new RuntimeException("Seller not found with id " + sellerId);
            }
            product.setSeller(sellerObjectId);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public Optional<ProductResponse> getProductWithSellerDetails(String id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            User sellerUser = null;
            SellerResponse sellerResponse = null;
            if (product.getSeller() != null) {
                sellerUser = userRepository.findById(product.getSeller().toHexString()).orElse(null);
                if (sellerUser != null) {
                    SellerProfile profile = null;
                    if (sellerUser.getSellerProfile() != null) {
                        profile = sellerProfileRepository.findById(sellerUser.getSellerProfile().toString())
                                .orElse(null);
                    }
                    sellerResponse = new SellerResponse(sellerUser, profile);
                }
            }
            return Optional.of(new ProductResponse(product, sellerResponse));
        }
        return Optional.empty();
    }

    public List<Product> getProductsBySeller(ObjectId sellerId) {
        return productRepository.findBySeller(sellerId);
    }
}
