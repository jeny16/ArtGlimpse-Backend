// package com.artglimpse.order.service;

// import com.artglimpse.buyer.model.product.Cart;
// import com.artglimpse.buyer.model.product.CartItem;
// import com.artglimpse.order.model.Order;
// import com.artglimpse.order.model.OrderItem;
// import com.artglimpse.buyer.model.profile.Address;
// import com.artglimpse.order.repository.OrderRepository;
// import com.artglimpse.product.repository.ProductRepository;
// import org.bson.types.ObjectId;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.stereotype.Service;
// import java.util.*;
// import com.artglimpse.buyer.repository.product.CartRepository;

// @Service
// public class OrderService {

//     @Autowired
//     private OrderRepository orderRepository;

//     @Autowired
//     private ProductRepository productRepository;

//     // Optional: to clear/update the cart after order placement
//     @Autowired(required = false)
//     private CartRepository cartRepository;

//     // Create orders for multi-vendor scenario: one order per seller.
//     public List<Order> createOrders(ObjectId userId, Cart cart, Address shippingAddress) {
//         // Group cart items by sellerId (retrieved from productData)
//         Map<ObjectId, List<CartItem>> itemsGroupedBySeller = new HashMap<>();

//         for (CartItem item : cart.getItems()) {
//             // Ensure productData is populated
//             if (item.getProductData() == null) {
//                 productRepository.findById(item.getProductId().toString())
//                         .ifPresent(item::setProductData);
//             }
//             ObjectId sellerId = null;
//             if (item.getProductData() != null && item.getProductData().getSeller() != null) {
//                 sellerId = item.getProductData().getSeller();
//             } else {
//                 throw new RuntimeException(
//                         "Product data missing seller information for product: " + item.getProductId());
//             }
//             itemsGroupedBySeller.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(item);
//         }

//         List<Order> createdOrders = new ArrayList<>();
//         // Create one order for each seller group.
//         for (Map.Entry<ObjectId, List<CartItem>> entry : itemsGroupedBySeller.entrySet()) {
//             ObjectId sellerId = entry.getKey();
//             List<CartItem> sellerItems = entry.getValue();

//             double totalMRP = 0.0;
//             double totalDiscount = 0.0;
//             double shippingCost = 0.0;
//             double couponDiscount = 0.0;
//             double donationAmount = cart.getDonationAmount() != null ? cart.getDonationAmount() : 0.0;
//             List<OrderItem> orderItems = new ArrayList<>();

//             for (CartItem item : sellerItems) {
//                 double price = item.getProductData().getPrice();
//                 int quantity = item.getQuantity();
//                 totalMRP += price * quantity;
//                 if (item.getDiscountPercent() != null) {
//                     totalDiscount += (price * item.getDiscountPercent() * quantity) / 100.0;
//                 }
//                 double itemShipping = item.getShippingCost() != null ? item.getShippingCost() : 0.0;
//                 shippingCost = Math.max(shippingCost, itemShipping);

//                 OrderItem orderItem = new OrderItem(item.getProductId(), item.getQuantity(), price);
//                 orderItems.add(orderItem);
//             }
//             if (cart.getCouponCode() != null && cart.getCouponCode().equals("NEWUSER")) {
//                 couponDiscount = totalMRP * 0.1;
//             }
//             double finalAmount = totalMRP - totalDiscount - couponDiscount + shippingCost + donationAmount;
//             Order order = new Order(userId, sellerId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
//             Order savedOrder = orderRepository.save(order);

//             // Populate product data for order items
//             savedOrder.getItems().forEach(item -> {
//                 productRepository.findById(item.getProductId().toString())
//                         .ifPresent(item::setProductData);
//             });
//             createdOrders.add(savedOrder);
//         }
//         // Optionally clear the cart after placing orders.
//         if (cartRepository != null) {
//             cart.setItems(new ArrayList<>());
//             cartRepository.save(cart);
//         }
//         return createdOrders;
//     }

//     public List<Order> getOrdersByUser(ObjectId userId) {
//         List<Order> orders = orderRepository.findByUserId(userId);
//         orders.forEach(order -> {
//             order.getItems().forEach(item -> {
//                 productRepository.findById(item.getProductId().toString())
//                         .ifPresent(item::setProductData);
//             });
//         });
//         return orders;
//     }

//     public List<Order> getOrdersForSeller(ObjectId sellerId, int page, int size) {
//         Page<Order> orderPage = orderRepository.findBySellerIdOrderByCreatedAtDesc(sellerId.toHexString(),
//                 PageRequest.of(page, size));
//         return orderPage.getContent();
//     }

//     public Order updateOrderStatus(String orderId, String newStatus) {
//         ObjectId oid = new ObjectId(orderId);
//         Order order = orderRepository.findById(oid)
//                 .orElseThrow(() -> new RuntimeException("Order not found"));
//         order.setPaymentStatus(newStatus);
//         return orderRepository.save(order);
//     }
// }

package com.artglimpse.order.service;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.product.CartItem;
import com.artglimpse.order.model.Order;
import com.artglimpse.order.model.OrderItem;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.order.repository.OrderRepository;
import com.artglimpse.product.repository.ProductRepository;
import com.artglimpse.product.service.ProductService;
import com.artglimpse.product.dto.ProductResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.*;
import com.artglimpse.buyer.repository.product.CartRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    // Optional: to clear/update the cart after order placement
    @Autowired(required = false)
    private CartRepository cartRepository;

    // Create orders for multi-vendor scenario: one order per seller.
    public List<Order> createOrders(ObjectId userId, Cart cart, Address shippingAddress) {
        // Group cart items by sellerId (retrieved from productData)
        Map<ObjectId, List<CartItem>> itemsGroupedBySeller = new HashMap<>();

        for (CartItem item : cart.getItems()) {
            // Ensure productData is minimally populated from the product repository
            if (item.getProductData() == null) {
                productRepository.findById(item.getProductId().toString())
                        .ifPresent(prod -> item.setProductData(prod));
            }
            ObjectId sellerId = null;
            if (item.getProductData() != null && item.getProductData().getSeller() != null) {
                sellerId = item.getProductData().getSeller();
            } else {
                throw new RuntimeException(
                        "Product data missing seller information for product: " + item.getProductId());
            }
            itemsGroupedBySeller.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(item);
        }

        List<Order> createdOrders = new ArrayList<>();
        // Create one order for each seller group.
        for (Map.Entry<ObjectId, List<CartItem>> entry : itemsGroupedBySeller.entrySet()) {
            ObjectId sellerId = entry.getKey();
            List<CartItem> sellerItems = entry.getValue();

            double totalMRP = 0.0;
            double totalDiscount = 0.0;
            double shippingCost = 0.0;
            double couponDiscount = 0.0;
            double donationAmount = cart.getDonationAmount() != null ? cart.getDonationAmount() : 0.0;
            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem item : sellerItems) {
                double price = item.getProductData().getPrice();
                int quantity = item.getQuantity();
                totalMRP += price * quantity;
                if (item.getDiscountPercent() != null) {
                    totalDiscount += (price * item.getDiscountPercent() * quantity) / 100.0;
                }
                double itemShipping = item.getShippingCost() != null ? item.getShippingCost() : 0.0;
                shippingCost = Math.max(shippingCost, itemShipping);

                OrderItem orderItem = new OrderItem(item.getProductId(), item.getQuantity(), price);
                orderItems.add(orderItem);
            }
            if (cart.getCouponCode() != null && cart.getCouponCode().equals("NEWUSER")) {
                couponDiscount = totalMRP * 0.1;
            }
            double finalAmount = totalMRP - totalDiscount - couponDiscount + shippingCost + donationAmount;
            Order order = new Order(userId, sellerId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
            Order savedOrder = orderRepository.save(order);

            // Populate productData for each order item using the DTO (with full seller
            // details)
            for (OrderItem item : savedOrder.getItems()) {
                Optional<ProductResponse> prodResponseOpt = productService
                        .getProductWithSellerDetails(item.getProductId().toString());
                prodResponseOpt.ifPresent(item::setProductData);
            }
            createdOrders.add(savedOrder);
        }
        // Optionally clear the cart after placing orders.
        if (cartRepository != null) {
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }
        return createdOrders;
    }

    public List<Order> getOrdersByUser(ObjectId userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                Optional<ProductResponse> prodResponseOpt = productService
                        .getProductWithSellerDetails(item.getProductId().toString());
                prodResponseOpt.ifPresent(item::setProductData);
            });
        });
        return orders;
    }

    public List<Order> getOrdersForSeller(ObjectId sellerId, int page, int size) {
        Page<Order> orderPage = orderRepository.findBySellerIdOrderByCreatedAtDesc(sellerId,
                PageRequest.of(page, size));
        return orderPage.getContent();
    }

    public Order updateOrderStatus(String orderId, String newStatus) {
        ObjectId oid = new ObjectId(orderId);
        Order order = orderRepository.findById(oid)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(newStatus);
        return orderRepository.save(order);
    }
}