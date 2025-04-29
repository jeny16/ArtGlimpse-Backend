package com.artglimpse.seller.controller;

import com.artglimpse.seller.dto.SellerProfileDTO;
import com.artglimpse.seller.dto.SellerProfileUpdateRequest;
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
    public ResponseEntity<SellerProfileDTO> updateProfile(@RequestParam String userId,
            @RequestBody SellerProfileUpdateRequest updateRequest) {
        SellerProfileDTO savedProfile = sellerProfileService.updateSellerProfile(userId, updateRequest);
        return ResponseEntity.ok(savedProfile);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteSellerProfile(@RequestParam String userId) {
        if (!sellerProfileService.existsById(userId)) {
            return ResponseEntity.status(404).body("Seller not found");
        }
        sellerProfileService.deleteSellerAndRelatedData(userId);
        return ResponseEntity.ok("Seller and related data deleted successfully");
    }
}
