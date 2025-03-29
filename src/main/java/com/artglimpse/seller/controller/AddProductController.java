package com.artglimpse.seller.controller;

import com.artglimpse.seller.dto.AddProductDTO;
import com.artglimpse.seller.model.AddProduct;
import com.artglimpse.seller.service.AddProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller/products")
@CrossOrigin(origins = {"http://localhost:5174"}, allowCredentials = "true")
public class AddProductController {

    @Autowired
    private AddProductService addProductService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<AddProduct> addProduct(
            @RequestPart("product") AddProductDTO addProductDTO,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) {
        AddProduct createdProduct = addProductService.addProduct(addProductDTO, images);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @GetMapping
    public List<AddProduct> getAllProducts() {
        return addProductService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddProduct> getProductById(@PathVariable String id) {
        Optional<AddProduct> product = addProductService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddProduct> updateProduct(@PathVariable String id, @RequestBody AddProduct product) {
        AddProduct updatedProduct = addProductService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        addProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
