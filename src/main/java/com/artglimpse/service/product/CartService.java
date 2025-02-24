package com.artglimpse.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.model.product.Cart;
import com.artglimpse.model.product.CartItem;
import com.artglimpse.repository.product.CartRepository;
import com.artglimpse.repository.auth.UserRepository;
import com.artglimpse.repository.product.ProductRepository;
import org.bson.types.ObjectId;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository; // Repository for user data

    @Autowired
    private ProductRepository productRepository; // Repository for product data

    // Add a product to the cart
    public Cart addProductToCart(String userIdStr, String productIdStr, int quantity) {
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId productId = new ObjectId(productIdStr);
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(productId, quantity));
        }
        Cart savedCart = cartRepository.save(cart);
        return populateCart(savedCart);
    }

    // Get the cart for a given user (populated with userData and productData)
    public Cart getCart(String userIdStr) {
        ObjectId userId = new ObjectId(userIdStr);
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));
        return populateCart(cart);
    }

    // Update the quantity of a product in the cart
    public Cart updateProductQuantity(String userIdStr, String productIdStr, int quantity) {
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId productId = new ObjectId(productIdStr);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
            Cart savedCart = cartRepository.save(cart);
            return populateCart(savedCart);
        }
        return null;
    }

    // Remove a product from the cart
    public Cart removeProductFromCart(String userIdStr, String productIdStr) {
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId productId = new ObjectId(productIdStr);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            Cart savedCart = cartRepository.save(cart);
            return populateCart(savedCart);
        }
        return null;
    }

    // Helper method to populate cart with userData and productData
    private Cart populateCart(Cart cart) {
        // Populate userData using the string representation of userId
        userRepository.findById(cart.getUserId().toString()).ifPresent(cart::setUserData);
        // For each cart item, populate productData
        for (CartItem item : cart.getItems()) {
            productRepository.findById(item.getProductId().toString())
                    .ifPresent(item::setProductData);
        }
        return cart;
    }
}
