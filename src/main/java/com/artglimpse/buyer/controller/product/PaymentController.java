package com.artglimpse.buyer.controller.product;

import com.artglimpse.buyer.service.product.StripePaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private StripePaymentService stripePaymentService;

    /**
     * Endpoint to create a Payment Intent.
     * Expects a JSON body with "amount" (in cents) and "currency" (e.g., "usd").
     * Returns the client secret to be used on the frontend.
     */
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody Map<String, Object> data) {
        Long amount = Long.valueOf(data.get("amount").toString());
        String currency = data.get("currency").toString();
        try {
            PaymentIntent intent = stripePaymentService.createPaymentIntent(amount, currency);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("clientSecret", intent.getClientSecret());
            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
