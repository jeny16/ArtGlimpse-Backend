package com.artglimpse.buyer.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.artglimpse.buyer.dto.auth.ProfileResponse;
import com.artglimpse.buyer.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.buyer.service.profile.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET merged profile endpoint (User + BuyerProfile)
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@RequestParam String userId) {
        ProfileResponse profile = userService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    // PUT endpoint to update profile data (both common user and buyer profile)
    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(@RequestParam String userId,
            @RequestBody UserProfileUpdateRequest request) {
        ProfileResponse updatedProfile = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(updatedProfile);
    }

    // DELETE endpoint to delete user and related data
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUserProfile(@RequestParam String userId) {
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userService.deleteUserAndRelatedData(userId);
        return ResponseEntity.ok("User and related data deleted successfully");
    }
}
