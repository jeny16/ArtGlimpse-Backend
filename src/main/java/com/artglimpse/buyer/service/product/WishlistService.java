package com.artglimpse.buyer.service.product;

import com.artglimpse.buyer.dto.product.WishlistResponse;
import com.artglimpse.buyer.model.product.Product;
import com.artglimpse.buyer.model.product.Wishlist;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.repository.product.ProductRepository;
import com.artglimpse.buyer.repository.product.WishlistRepository;
import com.artglimpse.buyer.repository.profile.BuyerProfileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyerProfileRepository buyerProfileRepository;

    // Add a product to a user's wishlist (returns the raw Wishlist)
    public Wishlist addProductToWishlist(String userIdStr, String productIdStr) {
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId productId = new ObjectId(productIdStr);
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElse(new Wishlist(null, userId, new ArrayList<>()));
        if (!wishlist.getProducts().contains(productId)) {
            wishlist.getProducts().add(productId);
        }
        return wishlistRepository.save(wishlist);
    }

    public WishlistResponse getWishlist(String userIdStr) {
        ObjectId userId = new ObjectId(userIdStr);
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElse(new Wishlist(null, userId, new ArrayList<>()));

        // Fetch the full BuyerProfile object using the string userId
        BuyerProfile user = buyerProfileRepository.findById(userIdStr).orElse(null);

        // Convert each ObjectId in wishlist.products to string IDs
        List<String> prodIdStrings = wishlist.getProducts().stream()
                .map(ObjectId::toString)
                .collect(Collectors.toList());
        // Fetch full Product objects
        List<Product> products = productRepository.findAllById(prodIdStrings);

        WishlistResponse response = new WishlistResponse();
        response.setUser(user);
        response.setProducts(products);
        return response;
    }

    // Remove a product from a user's wishlist (returns raw Wishlist)
    public Wishlist removeProductFromWishlist(String userIdStr, String productIdStr) {
        ObjectId userId = new ObjectId(userIdStr);
        ObjectId productId = new ObjectId(productIdStr);
        return wishlistRepository.findByUserId(userId)
                .map(wishlist -> {
                    wishlist.getProducts().remove(productId);
                    return wishlistRepository.save(wishlist);
                })
                .orElse(null);
    }
}
