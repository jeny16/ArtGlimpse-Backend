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
import com.artglimpse.buyer.repository.product.CartRepository;
import com.artglimpse.buyer.dto.auth.ProfileResponse;
import com.artglimpse.buyer.service.profile.UserService; // Service returning merged profile data
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    // Inject the UserService which merges User and BuyerProfile data
    @Autowired
    private UserService userService;

    // Optional: to clear/update the cart after order placement
    @Autowired(required = false)
    private CartRepository cartRepository;

    // Create a single order for the buyer
    public Order createOrders(ObjectId userId, Cart cart, Address shippingAddress) {
        List<OrderItem> orderItems = new ArrayList<>();
        double totalMRP = 0.0;
        double totalDiscount = 0.0;
        double shippingCost = 0.0;
        double couponDiscount = 0.0;
        double donationAmount = cart.getDonationAmount() != null ? cart.getDonationAmount() : 0.0;

        for (CartItem item : cart.getItems()) {
            // Ensure productData is populated
            if (item.getProductData() == null) {
                productRepository.findById(item.getProductId().toString())
                        .ifPresent(prod -> item.setProductData(prod));
            }
            double price = item.getProductData().getPrice();
            int quantity = item.getQuantity();
            totalMRP += price * quantity;
            if (item.getDiscountPercent() != null) {
                totalDiscount += (price * item.getDiscountPercent() * quantity) / 100.0;
            }
            double itemShipping = item.getShippingCost() != null ? item.getShippingCost() : 0.0;
            shippingCost = Math.max(shippingCost, itemShipping);
            orderItems.add(new OrderItem(item.getProductId(), quantity, price));
        }
        if (cart.getCouponCode() != null && cart.getCouponCode().equals("NEWUSER")) {
            couponDiscount = totalMRP * 0.1;
        }
        double finalAmount = totalMRP - totalDiscount - couponDiscount + shippingCost + donationAmount;

        Order order = new Order(userId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
        Order savedOrder = orderRepository.save(order);

        // Populate productData for each order item
        for (OrderItem item : savedOrder.getItems()) {
            Optional<ProductResponse> prodResponseOpt = productService
                    .getProductWithSellerDetails(item.getProductId().toString());
            prodResponseOpt.ifPresent(item::setProductData);
        }

        // Fetch and set the full user data (merged profile) instead of just the userId
        ProfileResponse profile = userService.getUserProfile(userId.toString());
        savedOrder.setUserData(profile);

        // Optionally clear the cart after order placement
        if (cartRepository != null) {
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }
        return savedOrder;
    }

    // Fetch orders by user (for buyer view)
    public List<Order> getOrdersByUser(ObjectId userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                Optional<ProductResponse> prodResponseOpt = productService
                        .getProductWithSellerDetails(item.getProductId().toString());
                prodResponseOpt.ifPresent(item::setProductData);
            });
            // Populate the userData field with the merged profile
            ProfileResponse profile = userService.getUserProfile(order.getUserId().toString());
            order.setUserData(profile);
        });
        return orders;
    }

    // Fetch orders for a seller (for seller view)
    public List<Order> getOrdersForSeller(ObjectId sellerId) {
        // Fetch all orders (or orders within a given period if needed)
        List<Order> orders = orderRepository.findAll();
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            List<OrderItem> matchingItems = new ArrayList<>();
            for (OrderItem item : order.getItems()) {
                Optional<ProductResponse> prodResponseOpt = productService
                        .getProductWithSellerDetails(item.getProductId().toString());
                if (prodResponseOpt.isPresent()) {
                    ProductResponse prodResp = prodResponseOpt.get();
                    if (prodResp.getSeller() != null &&
                            prodResp.getSeller().getId().equals(sellerId.toString())) {
                        // Set the productData before adding to matchingItems
                        item.setProductData(prodResp);
                        matchingItems.add(item);
                    }
                }
            }
            // If there are matching items, add a new Order object containing only these
            // items
            if (!matchingItems.isEmpty()) {
                Order sellerOrder = new Order();
                sellerOrder.setId(order.getId());
                sellerOrder.setUserId(order.getUserId());
                sellerOrder.setShippingAddress(order.getShippingAddress());
                sellerOrder.setCreatedAt(order.getCreatedAt());
                // Recalculate totalAmount based on matchingItems
                double sellerTotal = matchingItems.stream()
                        .mapToDouble(oi -> oi.getPrice() * oi.getQuantity())
                        .sum();
                sellerOrder.setTotalAmount(sellerTotal);
                sellerOrder.setPaymentStatus(order.getPaymentStatus());
                sellerOrder.setOrderStatus(order.getOrderStatus());
                sellerOrder.setItems(matchingItems);
                // Populate the userData field with the merged profile for the buyer
                ProfileResponse profile = userService.getUserProfile(order.getUserId().toString());
                sellerOrder.setUserData(profile);
                filteredOrders.add(sellerOrder);
            }
        }
        return filteredOrders;
    }

    // Update order status remains unchanged
    public Order updateOrderStatus(String orderId, String newStatus) {
        ObjectId oid = new ObjectId(orderId);
        Order order = orderRepository.findById(oid)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }
}
