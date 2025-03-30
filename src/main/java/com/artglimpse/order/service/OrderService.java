package com.artglimpse.order.service;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.product.CartItem;
import com.artglimpse.order.model.Order;
import com.artglimpse.order.model.OrderItem;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.order.repository.OrderRepository;
import com.artglimpse.product.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Optionally import CartRepository if you want to persist cart updates:
import com.artglimpse.buyer.repository.product.CartRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Using the common product repository to fetch product details
    @Autowired
    private ProductRepository productRepository;

    // Optional: to clear/update the cart after order placement
    @Autowired(required = false)
    private CartRepository cartRepository;

    public Order createOrder(ObjectId userId, Cart cart, Address shippingAddress) {
        double totalMRP = 0.0;
        double totalDiscount = 0.0;
        double shippingCost = 0.0;
        double couponDiscount = 0.0;
        double donationAmount = cart.getDonationAmount() != null ? cart.getDonationAmount() : 0.0;
        ObjectId sellerId = null;

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            double price = item.getProductData().getPrice();
            int quantity = item.getQuantity();
            totalMRP += price * quantity;
            if (item.getDiscountPercent() != null) {
                totalDiscount += (price * item.getDiscountPercent() * quantity) / 100.0;
            }
            double itemShipping = item.getShippingCost() != null ? item.getShippingCost() : 0.0;
            shippingCost = Math.max(shippingCost, itemShipping);

            if (sellerId == null && item.getProductData() != null && item.getProductData().getSeller() != null) {
                sellerId = item.getProductData().getSeller();
            }
            OrderItem orderItem = new OrderItem(item.getProductId(), item.getQuantity(),
                    item.getProductData().getPrice());
            orderItems.add(orderItem);
        }
        if (cart.getCouponCode() != null && cart.getCouponCode().equals("NEWUSER")) {
            couponDiscount = totalMRP * 0.1;
        }
        double finalAmount = totalMRP - totalDiscount - couponDiscount + shippingCost + donationAmount;
        Order order = new Order(userId, sellerId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
        Order savedOrder = orderRepository.save(order);

        // Populate each OrderItem with full product data
        savedOrder.getItems().forEach(item -> {
            productRepository.findById(item.getProductId().toString())
                    .ifPresent(item::setProductData);
        });

        // Optionally clear the cart items after placing the order
        if (cartRepository != null) {
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }

        return savedOrder;
    }

    public List<Order> getOrdersByUser(ObjectId userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        // Populate product data for each order item
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                productRepository.findById(item.getProductId().toString())
                        .ifPresent(item::setProductData);
            });
        });
        return orders;
    }

    public List<Order> getOrdersForSeller(ObjectId sellerId, int page, int size) {
        // Assuming the repository query expects a String representation of sellerId
        Page<Order> orderPage = orderRepository.findBySellerIdOrderByCreatedAtDesc(sellerId.toHexString(),
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