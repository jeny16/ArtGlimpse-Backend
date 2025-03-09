package com.artglimpse.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.artglimpse.model.auth.User;
import com.artglimpse.dto.auth.UserProfileUpdateRequest;
import com.artglimpse.service.auth.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET endpoint to fetch user profile data by userId (passed as query param)
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestParam String userId) {
        User user = userService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    // PUT endpoint to update the user profile (checkout details or profile edits)
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestParam String userId,
            @RequestBody UserProfileUpdateRequest request) {
        User updatedUser = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // NEW: DELETE endpoint to delete the entire user account and related data.
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUserProfile(@RequestParam String userId) {
        if (!userService.existsById(userId)) {
            return ResponseEntity.status(404).body("User not found");
        }
        userService.deleteUserAndRelatedData(userId);
        return ResponseEntity.ok("User and related data deleted successfully");
    }
}
