package com.artglimpse.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.model.auth.User;
import com.artglimpse.repository.auth.UserRepository;
import com.artglimpse.security.auth.JwtTokenUtil;
import com.artglimpse.service.auth.JwtResponse;
import com.artglimpse.service.auth.LoginRequest;
import com.artglimpse.service.auth.SignupRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // ---------------------------
    // Common Helper Methods
    // ---------------------------

    /**
     * Processes authentication for both buyer and seller.
     * If expectedRole is provided, it verifies the user has the required role.
     */
    private ResponseEntity<?> processAuthentication(LoginRequest loginRequest, String expectedRole) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details and user entity from the database
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

            // If an expected role is provided, verify it matches the user's role
            if (expectedRole != null && !expectedRole.equals(user.getRole())) {
                return ResponseEntity.status(401).body("Error: Unauthorized - Incorrect role");
            }

            // Generate JWT using the user details and MongoDB user ID
            String jwt = jwtTokenUtil.generateToken(userDetails, user.getId().toString());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getId()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Error: Invalid username or password");
        }
    }

    /**
     * Processes registration for both buyer and seller.
     * The role parameter determines the role assigned to the new user.
     */
    private ResponseEntity<?> processRegistration(SignupRequest signupRequest, String role) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        // Create a new user with the provided role (buyer or seller)
        User user = new User(
            signupRequest.getUsername(),
            signupRequest.getEmail(),
            passwordEncoder.encode(signupRequest.getPassword()),
            role
        );
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully with role: " + role);
    }

    // ---------------------------
    // Buyer Endpoints
    // ---------------------------

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        return processRegistration(signupRequest, "ROLE_USER");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // For buyer login, we don't need to enforce a specific role in the helper method.
        return processAuthentication(loginRequest, null);
    }

    // ---------------------------
    // Seller Endpoints
    // ---------------------------

    @PostMapping("/seller/signup")
    public ResponseEntity<?> registerSeller(@RequestBody SignupRequest signupRequest) {
        return processRegistration(signupRequest, "ROLE_SELLER");
    }

    @PostMapping("/seller/login")
    public ResponseEntity<?> authenticateSeller(@RequestBody LoginRequest loginRequest) {
        return processAuthentication(loginRequest, "ROLE_SELLER");
    }
}
