package com.artglimpse.service.seller;

import com.artglimpse.model.seller.Seller;
import com.artglimpse.repository.seller.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    // Retrieve all sellers
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    // Retrieve a seller by ID
    public Optional<Seller> getSellerById(String id) {
        return sellerRepository.findById(id);
    }

    // Create a new seller
    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    // Update an existing seller
    public Seller updateSeller(String id, Seller sellerDetails) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found with id " + id));
        seller.setName(sellerDetails.getName());
        seller.setLocation(sellerDetails.getLocation());
        seller.setRating(sellerDetails.getRating());
        seller.setDescription(sellerDetails.getDescription());
        seller.setContactEmail(sellerDetails.getContactEmail());
        seller.setContactPhone(sellerDetails.getContactPhone());
        seller.setWebsiteUrl(sellerDetails.getWebsiteUrl());
        seller.setUpdatedDate(sellerDetails.getUpdatedDate());
        return sellerRepository.save(seller);
    }

    // Delete a seller
    public void deleteSeller(String id) {
        sellerRepository.deleteById(id);
    }
}
