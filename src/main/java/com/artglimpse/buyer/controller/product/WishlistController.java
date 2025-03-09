package com.artglimpse.buyer.controller.product;

import com.artglimpse.buyer.dto.product.WishlistRequest;
import com.artglimpse.buyer.dto.product.WishlistResponse;
import com.artglimpse.buyer.model.product.Wishlist;
import com.artglimpse.buyer.service.product.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<Wishlist> addProductToWishlist(@RequestBody WishlistRequest request) {
        Wishlist updatedWishlist = wishlistService.addProductToWishlist(request.getUserId(), request.getProductId());
        return ResponseEntity.ok(updatedWishlist);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WishlistResponse> getWishlist(@PathVariable String userId) {
        WishlistResponse response = wishlistService.getWishlist(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable String userId,
                                                       @PathVariable String productId) {
        Wishlist updatedWishlist = wishlistService.removeProductFromWishlist(userId, productId);
        if (updatedWishlist != null) {
            return ResponseEntity.ok(updatedWishlist);
        }
        return ResponseEntity.notFound().build();
    }
}
