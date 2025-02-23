// package com.artglimpse.controller.product;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import com.artglimpse.model.product.Product;
// import com.artglimpse.service.product.ProductService;
// import java.util.List;

// @RestController
// @RequestMapping("/products")
// public class ProductController {

//     @Autowired
//     private ProductService productService;

//     // Public endpoint to fetch all products
//     @GetMapping
//     public List<Product> getAllProducts() {
//         return productService.getAllProducts();
//     }

//     // Public endpoint to fetch a specific product by ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Product> getProductById(@PathVariable String id) {
//         return productService.getProductById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     // Secured endpoint to create a new product (Admin only)
//     @PostMapping
//     // Uncomment the following annotation when using method-level security:
//     // @PreAuthorize("hasRole('ADMIN')")
//     public Product createProduct(@RequestBody Product product) {
//         return productService.createProduct(product);
//     }

//     // Secured endpoint to update an existing product (Admin only)
//     @PutMapping("/{id}")
//     // @PreAuthorize("hasRole('ADMIN')")
//     public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
//         return productService.updateProduct(id, product);
//     }

//     // Secured endpoint to delete a product (Admin only)
//     @DeleteMapping("/{id}")
//     // @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
//         productService.deleteProduct(id);
//         return ResponseEntity.noContent().build();
//     }

//     // Future endpoints for filterization, add-to-cart, add-to-wishlist, etc. can be
//     // added here.
// }

package com.artglimpse.controller.product;

import com.artglimpse.model.product.Product;
import com.artglimpse.service.product.ProductService;
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
