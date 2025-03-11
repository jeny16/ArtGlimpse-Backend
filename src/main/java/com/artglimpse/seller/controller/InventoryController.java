package com.artglimpse.seller.controller;
import com.artglimpse.seller.model.Inventory;
import com.artglimpse.seller.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // GET all inventory items
    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    // GET inventory item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Optional<Inventory> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST create new inventory item
    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    // PUT update inventory item
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable String id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        return ResponseEntity.ok(updatedInventory);
    }

    // DELETE inventory item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
