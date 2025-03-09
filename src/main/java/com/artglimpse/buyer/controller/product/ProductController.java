package com.artglimpse.buyer.controller.product;

import com.artglimpse.buyer.model.product.Product;
import com.artglimpse.buyer.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET a specific product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST to create a new product; expects sellerId as a request parameter
    @PostMapping
    public Product createProduct(@RequestBody Product product, @RequestParam String sellerId) {
        return productService.createProduct(product, sellerId);
    }

    // PUT to update an existing product; sellerId is optional here
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product,
            @RequestParam(required = false) String sellerId) {
        return productService.updateProduct(id, product, sellerId);
    }

    // DELETE a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
