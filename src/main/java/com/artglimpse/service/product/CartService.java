package com.artglimpse.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.model.product.Cart;
import com.artglimpse.model.product.CartItem;
import com.artglimpse.repository.product.CartRepository;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Add a product to the cart
    public Cart addProductToCart(String userId, String productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new java.util.ArrayList<>()));

        // Check if the product is already in the cart
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Option 1: Increase the quantity
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Add new product as a new item
            cart.getItems().add(new CartItem(productId, quantity));
        }
        return cartRepository.save(cart);
    }

    // Retrieve the cart for a given user
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new java.util.ArrayList<>()));
    }

    // Update the quantity of a specific product in the cart
    public Cart updateProductQuantity(String userId, String productId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
            return cartRepository.save(cart);
        }
        return null; // or throw an exception if preferred
    }

    // Remove a product from the cart
    public Cart removeProductFromCart(String userId, String productId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getItems().removeIf(item -> item.getProductId().equals(productId));
            return cartRepository.save(cart);
        }
        return null; // or throw an exception if preferred
    }
}
