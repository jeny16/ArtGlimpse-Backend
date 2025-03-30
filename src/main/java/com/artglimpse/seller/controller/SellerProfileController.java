package com.artglimpse.seller.controller;

import com.artglimpse.seller.dto.SellerProfileDTO;
import com.artglimpse.seller.model.SellerProfile;
import com.artglimpse.seller.service.SellerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerProfileController {

    @Autowired
    private SellerProfileService sellerProfileService;

    @GetMapping("/profile")
    public ResponseEntity<SellerProfileDTO> getSellerProfile(@RequestParam String userId) {
        SellerProfileDTO profileDTO = sellerProfileService.getSellerProfile(userId);
        if (profileDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profileDTO);
    }

    @PutMapping("/profile")
    public ResponseEntity<SellerProfile> updateProfile(@RequestParam String userId, @RequestBody SellerProfile updatedProfile) {
        SellerProfile savedProfile = sellerProfileService.updateSellerProfile(userId, updatedProfile);
        return ResponseEntity.ok(savedProfile);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<Void> deleteProfile(@RequestParam String userId) {
        sellerProfileService.deleteSellerProfile(userId);
        return ResponseEntity.noContent().build();
    }
}
