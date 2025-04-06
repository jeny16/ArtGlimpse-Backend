package com.artglimpse.buyer.service.product;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        // Initialize the Stripe API with your secret key
        Stripe.apiKey = apiKey;
    }

    /**
     * Creates a PaymentIntent with the given amount and currency.
     * 
     * @param amount   Amount in the smallest currency unit (e.g., cents)
     * @param currency Currency code (e.g., "usd")
     * @return PaymentIntent instance
     * @throws StripeException if an error occurs
     */
    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        // Accepting card payments only
        params.put("payment_method_types", java.util.List.of("card"));
        return PaymentIntent.create(params);
    }
}
