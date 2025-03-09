package com.artglimpse.buyer.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.artglimpse.buyer.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.service.profile.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET endpoint to fetch user profile data by userId (passed as query param)
    @GetMapping("/profile")
    public ResponseEntity<BuyerProfile> getProfile(@RequestParam String userId) {
        BuyerProfile profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    // PUT endpoint to update the user profile (checkout details or profile edits)
    @PutMapping("/profile")
    public ResponseEntity<BuyerProfile> updateProfile(@RequestParam String userId,
            @RequestBody UserProfileUpdateRequest request) {
        BuyerProfile updatedProfile = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(updatedProfile);
    }

    // DELETE endpoint to delete the entire user account and related data.
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUserProfile(@RequestParam String userId) {
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userService.deleteUserAndRelatedData(userId);
        return ResponseEntity.ok("User and related data deleted successfully");
    }
}
