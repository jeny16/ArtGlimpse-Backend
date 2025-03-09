package com.artglimpse.buyer.controller.product;

import com.artglimpse.buyer.model.profile.BuyerProfile;
import com.artglimpse.buyer.model.profile.PaymentMethod;
import com.artglimpse.buyer.repository.profile.BuyerProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private BuyerProfileRepository buyerProfileRepository;

    // Save a new payment method during checkout
    @PostMapping("/save")
    public ResponseEntity<?> savePaymentMethod(@RequestParam String userId,
                                               @RequestBody PaymentMethod paymentMethod) {
        BuyerProfile profile = buyerProfileRepository.findById(userId).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        profile.getPaymentMethods().add(paymentMethod);
        buyerProfileRepository.save(profile);
        return ResponseEntity.ok("Payment method saved successfully");
    }

    // Retrieve all payment methods for a user
    @GetMapping("/methods")
    public ResponseEntity<?> getPaymentMethods(@RequestParam String userId) {
        BuyerProfile profile = buyerProfileRepository.findById(userId).orElse(null);
        if (profile == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        List<PaymentMethod> methods = profile.getPaymentMethods();
        return ResponseEntity.ok(methods);
    }
}
