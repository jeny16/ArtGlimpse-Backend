// package com.artglimpse.product.controller;

// import com.artglimpse.product.dto.ProductResponse;
// import com.artglimpse.product.model.Product;
// import com.artglimpse.product.service.ProductService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.security.access.prepost.PreAuthorize;
// import java.util.List;
// import java.util.Optional;

// @RestController
// @RequestMapping("/products")
// public class ProductController {

//     @Autowired
//     private ProductService productService;

//     // Public endpoint: Get all products
//     @GetMapping
//     public List<Product> getAllProducts() {
//         return productService.getAllProducts();
//     }

//     // Public endpoint: Get basic product details by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Product> getProductById(@PathVariable String id) {
//         Optional<Product> productOpt = productService.getProductById(id);
//         return productOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     // New endpoint: Get detailed product info including full seller details using
//     // DTO
//     @GetMapping("/{id}/details")
//     public ResponseEntity<ProductResponse> getProductDetails(@PathVariable String id) {
//         Optional<ProductResponse> response = productService.getProductWithSellerDetails(id);
//         return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     // Seller-only endpoint: Create a new product (the sellerId parameter is
//     // required)
//     @PostMapping
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Product> createProduct(@RequestBody Product product, @RequestParam String sellerId) {
//         Product created = productService.createProduct(product, sellerId);
//         return ResponseEntity.status(201).body(created);
//     }

//     // Seller-only endpoint: Update an existing product
//     @PutMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Product> updateProduct(@PathVariable String id,
//             @RequestBody Product product,
//             @RequestParam(required = false) String sellerId) {
//         Product updated = productService.updateProduct(id, product, sellerId);
//         return ResponseEntity.ok(updated);
//     }

//     // Seller-only endpoint: Delete a product
//     @DeleteMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
//         productService.deleteProduct(id);
//         return ResponseEntity.noContent().build();
//     }
// }

package com.artglimpse.product.controller;

import com.artglimpse.product.dto.ProductResponse;
import com.artglimpse.product.model.Product;
import com.artglimpse.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Updated endpoint: Get all products with full seller details
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(product -> productService.getProductWithSellerDetails(product.getId())
                        .orElse(new ProductResponse(product, null)))
                .collect(Collectors.toList());
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
