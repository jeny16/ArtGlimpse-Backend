package com.artglimpse.seller.service;

import com.artglimpse.authentication.model.User;
import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.seller.model.SellerProfile;
import com.artglimpse.seller.repository.SellerProfileRepository;
import com.artglimpse.seller.dto.SellerProfileDTO;
import com.artglimpse.seller.dto.SellerProfileUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerProfileService {

    @Autowired
    private SellerProfileRepository sellerProfileRepo;

    @Autowired
    private UserRepository userRepository;

    public SellerProfileDTO getSellerProfile(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<SellerProfile> profileOpt = sellerProfileRepo.findById(userId);
        System.out.println("Fetching seller profile for userId: " + userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            SellerProfile profile = profileOpt.orElse(new SellerProfile());
            // Use profile name if exists; otherwise fallback to user's username
            String name = (profile.getName() != null && !profile.getName().isEmpty()) ? profile.getName()
                    : user.getUsername();
            String email = (profile.getEmail() != null && !profile.getEmail().isEmpty())
                    ? profile.getEmail()
                    : user.getEmail();
            return new SellerProfileDTO(
                    name,
                    email,
                    profile.getContactNumber(),
                    profile.getStoreName());
        }
        return null;
    }

    public SellerProfile updateSellerProfile(String userId, SellerProfileUpdateRequest updateRequest) {
        SellerProfile profile = sellerProfileRepo.findById(userId).orElse(new SellerProfile());
        profile.setId(userId);
        profile.setName(updateRequest.getName());
        profile.setEmail(updateRequest.getEmail());
        profile.setContactNumber(updateRequest.getContactNumber());
        profile.setStoreName(updateRequest.getStoreName());
        return sellerProfileRepo.save(profile);
    }

    public boolean existsById(String userId) {
        return sellerProfileRepo.existsById(userId);
    }

    public void deleteSellerAndRelatedData(String userId) {
        sellerProfileRepo.deleteById(userId);
        userRepository.deleteById(userId);
    }
}
