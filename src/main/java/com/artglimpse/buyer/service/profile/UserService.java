package com.artglimpse.buyer.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.buyer.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.repository.profile.BuyerProfileRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyerProfileRepository buyerProfileRepository;

    public BuyerProfile getUserProfile(String userId) {
        return buyerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Buyer profile not found with id: " + userId));
    }

    public BuyerProfile updateUserProfile(String userId, UserProfileUpdateRequest request) {
        BuyerProfile profile = buyerProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Buyer profile not found with id: " + userId));
        profile.setAddresses(request.getAddresses());
        profile.setMobile(request.getMobile());
        profile.setPaymentMethods(request.getPaymentMethods());
        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setHintName(request.getHintName());
        profile.setAlternateMobile(request.getAlternateMobile());
        return buyerProfileRepository.save(profile);
    }

    // Delete user and related buyer profile (plus other related data as needed)
    public void deleteUserAndRelatedData(String userId) {
        buyerProfileRepository.deleteById(userId);
        userRepository.deleteById(userId);
        // Also add deletion for cart, wishlist, orders, etc.
    }

    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }
}
