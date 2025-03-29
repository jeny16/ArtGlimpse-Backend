package com.artglimpse.seller.controller;

import com.artglimpse.seller.model.SellerProfile;
import com.artglimpse.seller.repository.SellerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller/profile")
public class SellerProfileController {

    @Autowired
    private SellerProfileRepository sellerProfileRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id) {
        return sellerProfileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable String id, @RequestBody SellerProfile updatedProfile) {
        return sellerProfileRepository.findById(id)
                .map(profile -> {
                    profile.setStoreName(updatedProfile.getStoreName());
                    profile.setContactNumber(updatedProfile.getContactNumber());
                    profile.setAddress(updatedProfile.getAddress());
                    sellerProfileRepository.save(profile);
                    return ResponseEntity.ok(profile);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
