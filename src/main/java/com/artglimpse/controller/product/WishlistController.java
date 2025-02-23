package com.artglimpse.controller.product;

import com.artglimpse.dto.product.WishlistRequest;
import com.artglimpse.model.product.Wishlist;
import com.artglimpse.service.product.WishlistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    /**
     * POST /wishlist → Add product to wishlist.
     * Expects a JSON body with userId and productId.
     */
    @PostMapping
    public ResponseEntity<Wishlist> addProductToWishlist(@RequestBody WishlistRequest request) {
        Wishlist updatedWishlist = wishlistService.addProductToWishlist(request.getUserId(), request.getProductId());
        return ResponseEntity.ok(updatedWishlist);
    }

    /**
     * GET /wishlist/{userId} → Get user's wishlist.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable String userId) {
        Wishlist wishlist = wishlistService.getWishlist(userId);
        return ResponseEntity.ok(wishlist);
    }

    /**
     * DELETE /wishlist/{userId}/{productId} → Remove a product from the wishlist.
     */
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Wishlist> removeProductFromWishlist(@PathVariable String userId,
            @PathVariable String productId) {
        Wishlist updatedWishlist = wishlistService.removeProductFromWishlist(userId, productId);
        if (updatedWishlist != null) {
            return ResponseEntity.ok(updatedWishlist);
        }
        return ResponseEntity.notFound().build();
    }
}
