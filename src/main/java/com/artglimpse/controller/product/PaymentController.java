package com.artglimpse.controller.product;

import com.artglimpse.model.auth.PaymentMethod;
import com.artglimpse.model.auth.User;
import com.artglimpse.repository.auth.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserRepository userRepository;

    // Save a new payment method during checkout
    @PostMapping("/save")
    public ResponseEntity<?> savePaymentMethod(@RequestParam String userId,
                                               @RequestBody PaymentMethod paymentMethod) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        user.getPaymentMethods().add(paymentMethod);
        userRepository.save(user);
        return ResponseEntity.ok("Payment method saved successfully");
    }

    // Retrieve all payment methods for a user
    @GetMapping("/methods")
    public ResponseEntity<?> getPaymentMethods(@RequestParam String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        List<PaymentMethod> methods = user.getPaymentMethods();
        return ResponseEntity.ok(methods);
    }
}
