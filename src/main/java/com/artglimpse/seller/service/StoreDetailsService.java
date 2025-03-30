package com.artglimpse.seller.service;

import com.artglimpse.seller.model.StoreDetails;
import com.artglimpse.seller.repository.StoreDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreDetailsService {

    @Autowired
    private StoreDetailsRepository repository;

    public Optional<StoreDetails> getStoreDetailsBySellerId(String sellerId) {
        return repository.findById(sellerId);
    }

    public StoreDetails saveOrUpdateStoreDetails(StoreDetails details) {
        return repository.save(details);
    }

    public void deleteStoreDetails(String sellerId) {
        repository.deleteById(sellerId);
    }
}
