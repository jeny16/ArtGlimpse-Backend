package com.artglimpse.buyer.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.buyer.dto.product.CartRequest;
import com.artglimpse.buyer.dto.product.UpdateCartRequest;
import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.service.product.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // POST /cart → Add product to cart
    @PostMapping
    public ResponseEntity<Cart> addProductToCart(@RequestBody CartRequest request) {
        Cart updatedCart = cartService.addProductToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    // GET /cart/{userId} → Get the user's cart (populated with userData and
    // productData)
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        Cart cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    // PUT /cart/{userId}/{productId} → Update product quantity in cart
    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<Cart> updateProductQuantity(
            @PathVariable String userId,
            @PathVariable String productId,
            @RequestBody UpdateCartRequest request) {
        Cart updatedCart = cartService.updateProductQuantity(userId, productId, request.getQuantity());
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /cart/{userId}/{productId} → Remove product from cart
    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        Cart updatedCart = cartService.removeProductFromCart(userId, productId);
        if (updatedCart != null) {
            return ResponseEntity.ok(updatedCart);
        }
        return ResponseEntity.notFound().build();
    }
}
