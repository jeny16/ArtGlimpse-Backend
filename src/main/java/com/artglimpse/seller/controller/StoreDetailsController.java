package com.artglimpse.seller.controller;

import com.artglimpse.seller.model.StoreDetails;
import com.artglimpse.seller.service.StoreDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller/store")
public class StoreDetailsController {

    @Autowired
    private StoreDetailsService storeDetailsService;

    @GetMapping
    public Optional<StoreDetails> getStoreDetails(@RequestParam String userId) {
        return storeDetailsService.getStoreDetailsBySellerId(userId);
    }

    @PutMapping
    public StoreDetails updateStoreDetails(@RequestParam String userId, @RequestBody StoreDetails details) {
        details.setSellerId(userId); // Ensures sellerId is set
        return storeDetailsService.saveOrUpdateStoreDetails(details);
    }

    @DeleteMapping
    public void deleteStoreDetails(@RequestParam String userId) {
        storeDetailsService.deleteStoreDetails(userId);
    }
}
