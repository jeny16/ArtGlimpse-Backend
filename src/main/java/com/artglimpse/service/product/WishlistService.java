package com.artglimpse.service.product;

import com.artglimpse.model.product.Wishlist;
import com.artglimpse.repository.product.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    // Add productId to a user's wishlist
    public Wishlist addProductToWishlist(String userId, String productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElse(new Wishlist(null, userId, null));
        if (wishlist.getProductIds() == null) {
            wishlist.setProductIds(new java.util.ArrayList<>());
        }
        // Avoid duplicates if necessary
        if (!wishlist.getProductIds().contains(productId)) {
            wishlist.getProductIds().add(productId);
        }
        return wishlistRepository.save(wishlist);
    }

    // Retrieve a user's wishlist
    public Wishlist getWishlist(String userId) {
        return wishlistRepository.findByUserId(userId)
                .orElse(new Wishlist(null, userId, new java.util.ArrayList<>()));
    }

    // Remove productId from a user's wishlist
    public Wishlist removeProductFromWishlist(String userId, String productId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findByUserId(userId);
        if (optionalWishlist.isPresent()) {
            Wishlist wishlist = optionalWishlist.get();
            wishlist.getProductIds().remove(productId);
            return wishlistRepository.save(wishlist);
        }
        // Optionally handle the case where no wishlist exists
        return null;
    }
}
