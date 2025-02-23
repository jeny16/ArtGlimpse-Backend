package com.artglimpse.service.product;

import com.artglimpse.model.product.Product;
import com.artglimpse.model.seller.Seller;
import com.artglimpse.repository.product.ProductRepository;
import com.artglimpse.repository.seller.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    // Fetch all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch a product by its ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    // Create a new product with a seller reference
    public Product createProduct(Product product, String sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with id " + sellerId));
        product.setSeller(seller);
        return productRepository.save(product);
    }

    // Update an existing product; if sellerId is provided, update the seller
    // reference as well
    public Product updateProduct(String id, Product productDetails, String sellerId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        product.setCategories(productDetails.getCategories());
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCurrency(productDetails.getCurrency());
        product.setStock(productDetails.getStock());
        product.setImages(productDetails.getImages());
        product.setDiscount(productDetails.isDiscount());
        product.setValid_Until_Discount(productDetails.getValid_Until_Discount());
        product.setPercentage_Discount(productDetails.getPercentage_Discount());

        if (sellerId != null && !sellerId.isEmpty()) {
            Seller seller = sellerRepository.findById(sellerId)
                    .orElseThrow(() -> new RuntimeException("Seller not found with id " + sellerId));
            product.setSeller(seller);
        }

        product.setProcessing_Time(productDetails.getProcessing_Time());
        product.setShipping_Time(productDetails.getShipping_Time());
        product.setShipping_Cost(productDetails.getShipping_Cost());
        product.setEstimated_Delivery(productDetails.getEstimated_Delivery());
        product.setCountries_Available(productDetails.getCountries_Available());
        product.setMaterials_Made(productDetails.getMaterials_Made());
        product.setTags(productDetails.getTags());

        return productRepository.save(product);
    }

    // Delete a product
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
