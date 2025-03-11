package com.artglimpse.seller.service;
import com.artglimpse.seller.model.Inventory;
import com.artglimpse.seller.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    // Get all inventory items
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    // Get inventory by ID
    public Optional<Inventory> getInventoryById(String id) {
        return inventoryRepository.findById(id);
    }

    // Create new inventory item
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    // Update inventory item
    public Inventory updateInventory(String id, Inventory inventoryDetails) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory item not found"));

        inventory.setName(inventoryDetails.getName());
        inventory.setCategory(inventoryDetails.getCategory());
        inventory.setStock(inventoryDetails.getStock());
        inventory.setPrice(inventoryDetails.getPrice());
        inventory.setStatus(inventoryDetails.getStatus());

        return inventoryRepository.save(inventory);
    }

    // Delete inventory item
    public void deleteInventory(String id) {
        inventoryRepository.deleteById(id);
    }
}
