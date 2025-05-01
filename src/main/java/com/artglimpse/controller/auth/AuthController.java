package com.artglimpse.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        // Create new user account
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                "ROLE_USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenUtil
                    .generateToken((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Error: Invalid username or password");
        }
    }
}

// @PostMapping("/login")
// public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//     try {
//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//         SecurityContextHolder.getContext().setAuthentication(authentication);

//         // Extract only the username (or email) from the authenticated user
//         org.springframework.security.core.userdetails.User userDetails = 
//                 (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        
//         // Pass only the username to the token generator
//         String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());
        
//         return ResponseEntity.ok(new JwtResponse(jwt));
//     } catch (BadCredentialsException ex) {
//         return ResponseEntity.status(401).body("Error: Invalid username or password");
//     }
// }

