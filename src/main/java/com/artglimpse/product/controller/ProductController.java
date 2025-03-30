package com.artglimpse.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.product.model.Product;
import com.artglimpse.product.service.ProductService;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Public endpoint: Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Public endpoint: Get product details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> productOpt = productService.getProductById(id);
        return productOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Seller-only endpoint: Create a new product (the sellerId parameter is
    // required)
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @RequestParam String sellerId) {
        Product created = productService.createProduct(product, sellerId);
        return ResponseEntity.status(201).body(created);
    }

    // Seller-only endpoint: Update an existing product
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Product> updateProduct(@PathVariable String id,
            @RequestBody Product product,
            @RequestParam(required = false) String sellerId) {
        Product updated = productService.updateProduct(id, product, sellerId);
        return ResponseEntity.ok(updated);
    }

    // Seller-only endpoint: Delete a product
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
