package com.artglimpse.seller.controller;

import com.artglimpse.seller.dto.StoreDetailsDTO;
import com.artglimpse.seller.model.StoreDetails;
import com.artglimpse.seller.service.StoreDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller/store")
public class StoreDetailsController {

    @Autowired
    private StoreDetailsService storeDetailsService;

    @GetMapping
    public ResponseEntity<StoreDetails> getStoreDetails(@RequestParam String userId) {
        Optional<StoreDetails> detailsOpt = storeDetailsService.getStoreDetailsBySellerId(userId);
        if (detailsOpt.isPresent()) {
            return ResponseEntity.ok(detailsOpt.get());
        } else {
            // For a new seller, return an empty StoreDetails object with the sellerId set.
            StoreDetails emptyDetails = new StoreDetails();
            emptyDetails.setSellerId(userId);
            return ResponseEntity.ok(emptyDetails);
        }
    }

    @PutMapping
    public ResponseEntity<StoreDetails> updateStoreDetails(@RequestParam String userId,
            @RequestBody StoreDetailsDTO dto) {
        StoreDetails details = new StoreDetails();
        details.setSellerId(userId);
        details.setStoreName(dto.getStoreName());
        details.setOwnerName(dto.getOwnerName());
        details.setGstNumber(dto.getGstNumber());
        details.setAadharNumber(dto.getAadharNumber());
        details.setPanNumber(dto.getPanNumber());
        // Set individual address fields
        details.setAddressStreet(dto.getAddressStreet());
        details.setAddressCity(dto.getAddressCity());
        details.setAddressState(dto.getAddressState());
        details.setAddressZip(dto.getAddressZip());
        details.setAddressCountry(dto.getAddressCountry());
        details.setDescription(dto.getDescription());
        details.setStoreLogoUrl(dto.getStoreLogoUrl());
        details.setStoreBannerUrl(dto.getStoreBannerUrl());
        details.setCategories(dto.getCategories());
        details.setWebsite(dto.getWebsite());
        details.setEstablishedDate(storeDetailsService.parseEstablishedDate(dto.getEstablishedDate()));
        details.setReturnPolicy(dto.getReturnPolicy());
        // Set new store contact fields
        details.setStoreMobile(dto.getStoreMobile());
        details.setStoreEmail(dto.getStoreEmail());

        StoreDetails updatedDetails = storeDetailsService.saveOrUpdateStoreDetails(details);
        return ResponseEntity.ok(updatedDetails);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStoreDetails(@RequestParam String userId) {
        storeDetailsService.deleteStoreDetails(userId);
        return ResponseEntity.ok("Store details deleted successfully");
    }
}
