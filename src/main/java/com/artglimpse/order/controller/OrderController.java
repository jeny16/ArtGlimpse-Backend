// package com.artglimpse.order.controller;

// import com.artglimpse.order.dto.CreateOrderRequest;
// import com.artglimpse.order.model.Order;
// import com.artglimpse.order.service.OrderService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.bson.types.ObjectId;
// import org.springframework.security.access.prepost.PreAuthorize;
// import java.util.List;

// @RestController
// @RequestMapping("/orders")
// public class OrderController {

//     @Autowired
//     private OrderService orderService;

//     @PostMapping
//     @PreAuthorize("hasRole('ROLE_USER')")
//     public ResponseEntity<List<Order>> createOrders(@RequestBody CreateOrderRequest request) {
//         List<Order> orders = orderService.createOrders(request.getUserId(), request.getCart(),
//                 request.getShippingAddress());
//         return ResponseEntity.ok(orders);
//     }

//     @GetMapping("/{userId}")
//     @PreAuthorize("hasRole('ROLE_USER')")
//     public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String userId) {
//         ObjectId userObjectId = new ObjectId(userId);
//         List<Order> orders = orderService.getOrdersByUser(userObjectId);
//         return ResponseEntity.ok(orders);
//     }

//     @GetMapping("/seller/{sellerId}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<List<Order>> getOrdersForSeller(@PathVariable String sellerId,
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size) {
//         List<Order> orders = orderService.getOrdersForSeller(new ObjectId(sellerId), page, size);
//         return ResponseEntity.ok(orders);
//     }

//     @PatchMapping("/{orderId}")
//     @PreAuthorize("hasRole('ROLE_SELLER')")
//     public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestBody String newStatus) {
//         Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
//         return ResponseEntity.ok(updatedOrder);
//     }
// }

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

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Order>> createOrders(@RequestBody CreateOrderRequest request) {
        List<Order> orders = orderService.createOrders(request.getUserId(), request.getCart(),
                request.getShippingAddress());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable String userId) {
        ObjectId userObjectId = new ObjectId(userId);
        List<Order> orders = orderService.getOrdersByUser(userObjectId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/seller/{sellerId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<List<Order>> getOrdersForSeller(@PathVariable String sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Order> orders = orderService.getOrdersForSeller(new ObjectId(sellerId), page, size);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestBody String newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
