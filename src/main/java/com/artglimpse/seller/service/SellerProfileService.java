package com.artglimpse.seller.service;

import com.artglimpse.authentication.model.User;
import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.seller.model.SellerProfile;
import com.artglimpse.seller.model.StoreDetails;
import com.artglimpse.seller.repository.SellerProfileRepository;
import com.artglimpse.seller.repository.StoreDetailsRepository;
import com.artglimpse.seller.dto.SellerProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerProfileService {

    @Autowired
    private SellerProfileRepository sellerProfileRepo;

    @Autowired
    private StoreDetailsRepository storeDetailsRepo;

    @Autowired
    private UserRepository userRepository;

    public SellerProfileDTO getSellerProfile(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<SellerProfile> profileOpt = sellerProfileRepo.findById(userId);
        System.out.println("Fetching profile for userId: " + userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            SellerProfile profile = profileOpt.orElse(new SellerProfile());

            return new SellerProfileDTO(
                user.getUsername(),
                user.getEmail(),
                profile.getContactNumber(),
                profile.getAddress()
            );
        }
        return null;
    }

    public SellerProfile updateSellerProfile(String userId, SellerProfile profileData) {
        profileData.setId(userId); // Ensure ID is set
        return sellerProfileRepo.save(profileData);
    }

    public boolean existsById(String userId) {
        return sellerProfileRepo.existsById(userId);
    }

    public void deleteSellerProfile(String userId) {
        sellerProfileRepo.deleteById(userId);
        // Optional: delete related store details, etc.
    }

    public Optional<StoreDetails> getStoreDetails(String sellerId) {
        return storeDetailsRepo.findById(sellerId);
    }

    public StoreDetails updateStoreDetails(StoreDetails updatedStore) {
        return storeDetailsRepo.save(updatedStore);
    }
}
