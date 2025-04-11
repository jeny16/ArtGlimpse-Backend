// package com.artglimpse.product.controller;

// import com.artglimpse.product.dto.ProductResponse;
// import com.artglimpse.product.model.Product;
// import com.artglimpse.product.service.ProductService;

// import org.bson.types.ObjectId;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.security.access.prepost.PreAuthorize;

// import java.util.Base64;
// import java.util.Collections;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/products")
// public class ProductController {

//     @Autowired
//     private ProductService productService;

//     // Updated endpoint: Get all products with full seller details
//     @GetMapping
//     public List<ProductResponse> getAllProducts() {
//         List<Product> products = productService.getAllProducts();
//         return products.stream()
//                 .map(product -> productService.getProductWithSellerDetails(product.getId())
//                         .orElse(new ProductResponse(product, null)))
//                 .collect(Collectors.toList());
//     }

//     // Public endpoint: Get basic product details by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Product> getProductById(@PathVariable String id) {
//         Optional<Product> productOpt = productService.getProductById(id);
//         return productOpt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     @GetMapping("/{id}/details")
//     public ResponseEntity<ProductResponse> getProductDetails(@PathVariable String id) {
//         Optional<ProductResponse> response = productService.getProductWithSellerDetails(id);
//         return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
// @PreAuthorize("hasRole('ROLE_SELLER')")
// public ResponseEntity<Product> createProduct(
//         @RequestParam("name") String name,
//         @RequestParam("description") String description,
//         @RequestParam("price") double price,
//         @RequestParam("stock") int stock,
//         @RequestParam("category") String category,
//         @RequestParam("discount") boolean discount,
//         @RequestParam(value = "percentage_Discount", required = false) int percentageDiscount,
//         @RequestParam("materials_Made") List<String> materialsMade,
//         @RequestParam("tags") List<String> tags,
//         @RequestParam("sellerId") String sellerId,
//         @RequestParam("images") List<String> imageIds // ✅ Image IDs from Appwrite
// ) {
//     try {
//         Product product = new Product();
//         product.setName(name);
//         product.setDescription(description);
//         product.setPrice(price);
//         product.setStock(stock);
//         product.setCategories(category);
//         product.setDiscount(discount);
//         product.setPercentage_Discount(percentageDiscount);
//         product.setMaterials_Made(materialsMade);
//         product.setTags(tags);
//         product.setImages(imageIds); // ✅ save Appwrite image IDs

//         // 💰 Set default currency as INR
//         product.setCurrency("INR");

//         Product createdProduct = productService.createProduct(product, sellerId);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//     } catch (Exception e) {
//         e.printStackTrace(); // 💡 helpful during debugging
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//     }
// }

//     @PatchMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Product> updateProduct(
//             @PathVariable String id,
//             @RequestBody Map<String, Object> updates,
//             @RequestParam(required = false) String sellerId) {
//         Product updatedProduct = productService.updateProduct(id, updates, sellerId);
//         return ResponseEntity.ok(updatedProduct);
//     }

//     @DeleteMapping("/{id}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
//         productService.deleteProduct(id);
//         return ResponseEntity.noContent().build();
//     }

//     @GetMapping("/seller")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<?> getProductsBySeller(@RequestParam String sellerId) {
//         if (!ObjectId.isValid(sellerId)) {
//             return ResponseEntity.badRequest().body("Invalid sellerId format.");
//         }
//         ObjectId sellerObjectId = new ObjectId(sellerId);
//         List<Product> products = productService.getProductsBySeller(sellerObjectId);
//         List<ProductResponse> productResponses = products.stream()
//                 .map(product -> productService.getProductWithSellerDetails(product.getId())
//                         .orElse(new ProductResponse(product, null)))
//                 .collect(Collectors.toList());
//         return ResponseEntity.ok(productResponses);
//     }
// }

package com.artglimpse.product.controller;

import com.artglimpse.product.dto.ProductResponse;
import com.artglimpse.product.model.Product;
import com.artglimpse.product.service.ProductService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{id}/details")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable String id) {
        Optional<ProductResponse> response = productService.getProductWithSellerDetails(id);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("category") String category,
            @RequestParam("discount") boolean discount,
            @RequestParam(value = "percentage_Discount", required = false) int percentageDiscount,
            @RequestParam("materials_Made") String materialsMade, // CSV string, split later if needed
            @RequestParam("tags") List<String> tags,
            @RequestParam("valid_Until_Discount") String validUntil, // expected YYYY-MM-DD
            @RequestParam("processing_Time") String processingTime,
            @RequestParam("shipping_Time") String shippingTime,
            @RequestParam("shipping_Cost") double shippingCost,
            @RequestParam("estimated_Delivery") String estimatedDelivery,
            @RequestParam("countries_Available") String countriesAvailable, // CSV string
            @RequestParam("sellerId") String sellerId,
            @RequestParam("images") List<String> imageIds) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategories(category);
            product.setDiscount(discount);
            product.setPercentage_Discount(percentageDiscount);
            // Process materials_Made from CSV to List<String>
            List<String> materialsList = List.of(materialsMade.split("\\s*,\\s*"));
            product.setMaterials_Made(materialsList);
            product.setTags(tags);
            product.setValid_Until_Discount(LocalDate.parse(validUntil));
            product.setProcessing_Time(processingTime);
            product.setShipping_Time(shippingTime);
            product.setShipping_Cost(shippingCost);
            product.setEstimated_Delivery(estimatedDelivery);
            // Process countries_Available from CSV to List<String>
            List<String> countriesList = List.of(countriesAvailable.split("\\s*,\\s*"));
            product.setCountries_Available(countriesList);
            product.setImages(imageIds); // Save image IDs or URLs

            // Set default currency as INR
            product.setCurrency("INR");

            Product createdProduct = productService.createProduct(product, sellerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates,
            @RequestParam(required = false) String sellerId) {
        Product updatedProduct = productService.updateProduct(id, updates, sellerId);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

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
