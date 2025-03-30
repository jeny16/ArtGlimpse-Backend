package com.artglimpse.order.controller;

import com.artglimpse.order.dto.CreateOrderRequest;
import com.artglimpse.order.model.Order;
import com.artglimpse.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Buyer endpoint: Create an order at checkout.
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.getUserId(), request.getCart(), request.getShippingAddress());
        return ResponseEntity.ok(order);
    }

    // Buyer endpoint: Retrieve buyer's own orders.
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String userId) {
        ObjectId userObjectId = new ObjectId(userId);
        List<Order> orders = orderService.getOrdersByUser(userObjectId);
        return ResponseEntity.ok(orders);
    }

    // Seller endpoint: Retrieve orders for a specific seller.
    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<List<Order>> getOrdersForSeller(@PathVariable String sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Here we convert the sellerId string to ObjectId where needed.
        List<Order> orders = orderService.getOrdersForSeller(new ObjectId(sellerId), page, size);
        return ResponseEntity.ok(orders);
    }

    // Seller endpoint: Update the order payment status.
    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestBody String newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
