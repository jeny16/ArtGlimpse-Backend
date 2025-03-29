package com.artglimpse.buyer.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.buyer.dto.auth.ProfileResponse;
import com.artglimpse.buyer.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.repository.profile.BuyerProfileRepository;
import com.artglimpse.authentication.model.User;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BuyerProfileRepository buyerProfileRepository;

        // Merge common User data with BuyerProfile data
        public ProfileResponse getUserProfile(String userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                BuyerProfile profile = buyerProfileRepository.findById(userId)
                                .orElse(new BuyerProfile());
                return new ProfileResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                profile.getMobile(),
                                profile.getGender(),
                                profile.getDateOfBirth(),
                                profile.getHintName(),
                                profile.getAlternateMobile(),
                                profile.getAddresses(),
                                profile.getPaymentMethods());
        }

        // Updated updateUserProfile method: replace the addresses field entirely
        public ProfileResponse updateUserProfile(String userId, UserProfileUpdateRequest request) {
                // Update common user fields
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                        user.setUsername(request.getUsername());
                }
                if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                        user.setEmail(request.getEmail());
                }
                userRepository.save(user);

                // Update buyer profile fields
                BuyerProfile profile = buyerProfileRepository.findById(userId).orElse(new BuyerProfile());
                profile.setId(userId);

                // Replace addresses entirely with the incoming addresses array
                if (request.getAddresses() != null) {
                        profile.setAddresses(request.getAddresses());
                }
                // Update other profile fields as needed
                if (request.getMobile() != null && !request.getMobile().isEmpty()) {
                        profile.setMobile(request.getMobile());
                }
                if (request.getPaymentMethods() != null && !request.getPaymentMethods().isEmpty()) {
                        profile.setPaymentMethods(request.getPaymentMethods());
                }
                if (request.getGender() != null && !request.getGender().isEmpty()) {
                        profile.setGender(request.getGender());
                }
                if (request.getDateOfBirth() != null && !request.getDateOfBirth().isEmpty()) {
                        profile.setDateOfBirth(request.getDateOfBirth());
                }
                if (request.getHintName() != null && !request.getHintName().isEmpty()) {
                        profile.setHintName(request.getHintName());
                }
                if (request.getAlternateMobile() != null && !request.getAlternateMobile().isEmpty()) {
                        profile.setAlternateMobile(request.getAlternateMobile());
                }
                BuyerProfile updatedProfile = buyerProfileRepository.save(profile);

                return new ProfileResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                updatedProfile.getMobile(),
                                updatedProfile.getGender(),
                                updatedProfile.getDateOfBirth(),
                                updatedProfile.getHintName(),
                                updatedProfile.getAlternateMobile(),
                                updatedProfile.getAddresses(),
                                updatedProfile.getPaymentMethods());
        }

        public void deleteUserAndRelatedData(String userId) {
                buyerProfileRepository.deleteById(userId);
                userRepository.deleteById(userId);
                // Add deletion for other related data (cart, wishlist, orders) if needed.
        }

        public boolean existsById(String userId) {
                return userRepository.existsById(userId);
        }
}
