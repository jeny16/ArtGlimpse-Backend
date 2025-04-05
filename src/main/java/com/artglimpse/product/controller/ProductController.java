package com.artglimpse.product.controller;

import com.artglimpse.product.dto.ProductResponse;
import com.artglimpse.product.model.Product;
import com.artglimpse.product.service.ProductService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Public endpoint: Get basic product details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> productOpt = productService.getProductById(id);
        return productOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint: Get detailed product info including full seller details using DTO
    @GetMapping("/{id}/details")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable String id) {
        Optional<ProductResponse> response = productService.getProductWithSellerDetails(id);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Seller-only endpoint: Update an existing product
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates,
            @RequestParam(required = false) String sellerId) {
        Product updatedProduct = productService.updateProduct(id, updates, sellerId);
        return ResponseEntity.ok(updatedProduct);
    }

    // Seller-only endpoint: Delete a product
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint: Get products by seller ID
    @GetMapping("/seller")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<?> getProductsBySeller(@RequestParam String sellerId) {
        if (!ObjectId.isValid(sellerId)) {
            return ResponseEntity.badRequest().body("Invalid sellerId format.");
        }
        ObjectId sellerObjectId = new ObjectId(sellerId);
        List<Product> products = productService.getProductsBySeller(sellerObjectId);
        List<ProductResponse> productResponses = products.stream()
                .map(product -> productService.getProductWithSellerDetails(product.getId())
                        .orElse(new ProductResponse(product, null)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
    
    
}
