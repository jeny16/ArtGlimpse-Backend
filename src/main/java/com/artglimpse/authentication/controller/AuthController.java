package com.artglimpse.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.authentication.repository.UserRepository;
import com.artglimpse.authentication.dto.JwtResponse;
import com.artglimpse.authentication.dto.LoginRequest;
import com.artglimpse.authentication.dto.SignupRequest;
import com.artglimpse.authentication.model.User;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.repository.profile.BuyerProfileRepository;
import com.artglimpse.seller.model.SellerProfile;
import com.artglimpse.seller.repository.SellerProfileRepository;
import com.artglimpse.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyerProfileRepository buyerProfileRepository;

    @Autowired
    private SellerProfileRepository sellerProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ResponseEntity<?> processAuthentication(LoginRequest loginRequest, String expectedRole) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with email: " + loginRequest.getEmail()));

            if (expectedRole != null && !expectedRole.equals(user.getRole())) {
                return ResponseEntity.status(401).body("Error: Unauthorized - Incorrect role");
            }

            String jwt = jwtTokenUtil.generateToken(userDetails, user.getId());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getId()));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Error: Invalid username or password");
        }
    }

    private ResponseEntity<?> processRegistration(SignupRequest signupRequest, String role) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User newUser = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                role,
                null,
                null);

        newUser = userRepository.save(newUser);

        if ("ROLE_USER".equals(role)) {
            BuyerProfile buyerProfile = new BuyerProfile();
            buyerProfile.setId(newUser.getId());
            buyerProfileRepository.save(buyerProfile);
            newUser.setBuyerProfile(buyerProfile);
        }

        if ("ROLE_SELLER".equals(role)) {
            SellerProfile sellerProfile = new SellerProfile();
            sellerProfile.setId(newUser.getId());
            sellerProfileRepository.save(sellerProfile);
            newUser.setSellerProfile(sellerProfile);
        }

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully with role: " + role);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        return processRegistration(signupRequest, "ROLE_USER");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return processAuthentication(loginRequest, null);
    }

    @PostMapping("/seller/signup")
    public ResponseEntity<?> registerSeller(@RequestBody SignupRequest signupRequest) {
        return processRegistration(signupRequest, "ROLE_SELLER");
    }

    @PostMapping("/seller/login")
    public ResponseEntity<?> authenticateSeller(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

            if (!"ROLE_SELLER".equals(user.getRole())) {
                return ResponseEntity.status(401).body("Error: Unauthorized - Incorrect role");
            }

            String jwt = jwtTokenUtil.generateToken(userDetails, user.getId());

            SellerProfile sellerProfile = sellerProfileRepository.findById(user.getId()).orElse(null);

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("userId", user.getId());

            if (sellerProfile != null) {
                response.put("sellerId", sellerProfile.getId());
            }

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Error: Invalid username or password");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }
        String token = header.substring(7);
        try {
            Claims claims = jwtTokenUtil.getClaimsFromToken(token);
            String userId = claims.getSubject();
            if (!userRepository.existsById(userId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User no longer exists");
            }
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
