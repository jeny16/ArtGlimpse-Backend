package com.artglimpse.controller.seller;

import com.artglimpse.model.seller.Seller;
import com.artglimpse.service.seller.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // GET all sellers
    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }

    // GET seller by ID
    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable String id) {
        return sellerService.getSellerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create new seller
    @PostMapping
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    // PUT update seller
    @PutMapping("/{id}")
    public Seller updateSeller(@PathVariable String id, @RequestBody Seller seller) {
        return sellerService.updateSeller(id, seller);
    }

    // DELETE seller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable String id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}
