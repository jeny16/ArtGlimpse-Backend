package com.artglimpse.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.model.auth.User;
import com.artglimpse.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.repository.auth.UserRepository;
import com.artglimpse.repository.product.CartRepository;
import com.artglimpse.repository.product.WishlistRepository;
import org.bson.types.ObjectId;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    public User getUserProfile(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public User updateUserProfile(String userId, UserProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setAddresses(request.getAddresses());
        user.setMobile(request.getMobile());
        user.setPaymentMethods(request.getPaymentMethods());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setHintName(request.getHintName());
        user.setAlternateMobile(request.getAlternateMobile());

        return userRepository.save(user);
    }

    // New method: Delete user and its related data
    public void deleteUserAndRelatedData(String userId) {
        // Delete Cart if exists
        ObjectId userObjectId = new ObjectId(userId);
        cartRepository.findByUserId(userObjectId).ifPresent(cartRepository::delete);

        // Delete Wishlist if exists
        wishlistRepository.findByUserId(userObjectId).ifPresent(wishlistRepository::delete);

        // (If you have orders or other related collections, add deletion logic here.)

        // Finally, delete the user
        userRepository.deleteById(userId);
    }

    // New method to check if user exists
    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }
}
