package com.artglimpse.seller.controller;

import com.artglimpse.seller.dto.PaymentStatusUpdateDTO;
import com.artglimpse.seller.model.OrdersList;
import com.artglimpse.seller.service.SellerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordersList")
@CrossOrigin(origins = "http://localhost:5154")
public class OrdersListController {

    @Autowired
    private SellerOrderService ordersListService;

    // Endpoint to get recent orders for a seller
    @GetMapping("/recent/{sellerId}")
    public ResponseEntity<?> getRecentOrders(
            @PathVariable String sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Use sellerId directly as a string (no conversion)
            List<OrdersList> orders = ordersListService.getRecentOrdersForSeller(sellerId, page, size);
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No orders found for seller ID: " + sellerId);
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching orders: " + e.getMessage());
        }
    }
    
    // Endpoint to update an order's status via PATCH
    @PatchMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody PaymentStatusUpdateDTO dto) {
        try {
            String newStatus = dto.getPaymentStatus();
            if (newStatus == null || newStatus.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("paymentStatus is required.");
            }
            OrdersList updatedOrder = ordersListService.updateOrderStatus(orderId, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating order status: " + e.getMessage());
        }
    }
}
