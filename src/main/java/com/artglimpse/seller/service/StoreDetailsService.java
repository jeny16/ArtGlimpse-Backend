package com.artglimpse.seller.service;

import com.artglimpse.seller.model.StoreDetails;
import com.artglimpse.seller.repository.StoreDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class StoreDetailsService {

    @Autowired
    private StoreDetailsRepository repository;

    // Formatter for "dd-MM-yyyy"
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Optional<StoreDetails> getStoreDetailsBySellerId(String sellerId) {
        return repository.findById(sellerId);
    }

    public StoreDetails saveOrUpdateStoreDetails(StoreDetails details) {
        return repository.save(details);
    }

    public void deleteStoreDetails(String sellerId) {
        repository.deleteById(sellerId);
    }

    // Parse a date string in dd-MM-yyyy format to LocalDate
    public LocalDate parseEstablishedDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            System.err.println("Error parsing established date: " + e.getMessage());
            return null;
        }
    }
}
