package com.artglimpse.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artglimpse.dto.product.CreateOrderRequest;
import com.artglimpse.model.product.Order;
import com.artglimpse.service.product.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.getUserId(), request.getCart(), request.getShippingAddress());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrders(@PathVariable String userId) {
        List<Order> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }
}
