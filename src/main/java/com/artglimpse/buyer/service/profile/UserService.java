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

        // Updated updateUserProfile method: create BuyerProfile if not present
        public ProfileResponse updateUserProfile(String userId, UserProfileUpdateRequest request) {
                // Update common user fields using fullName and email from the request
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                user.setUsername(request.getUsername());
                user.setEmail(request.getEmail());
                userRepository.save(user);

                // Update or create buyer profile fields
                BuyerProfile profile = buyerProfileRepository.findById(userId).orElse(null);
                if (profile == null) {
                        profile = new BuyerProfile();
                        profile.setId(userId);
                }
                profile.setAddresses(request.getAddresses());
                profile.setMobile(request.getMobile());
                profile.setPaymentMethods(request.getPaymentMethods());
                profile.setGender(request.getGender());
                profile.setDateOfBirth(request.getDateOfBirth());
                profile.setHintName(request.getHintName());
                profile.setAlternateMobile(request.getAlternateMobile());
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
