package com.artglimpse.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.artglimpse.model.product.Cart;
import com.artglimpse.model.product.CartItem;
import com.artglimpse.model.product.Order;
import com.artglimpse.model.product.OrderItem;
import com.artglimpse.repository.product.CartRepository;
import com.artglimpse.repository.product.OrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    public Order createOrder(String userId, Cart cart, com.artglimpse.model.auth.Address shippingAddress) {
        double totalMRP = 0.0;
        double totalDiscount = 0.0;
        double shippingCost = 0.0;
        double couponDiscount = 0.0;
        double donationAmount = cart.getDonationAmount() != null ? cart.getDonationAmount() : 0.0;

        // Compute totals based on cart items
        for (CartItem item : cart.getItems()) {
            double price = item.getProductData().getPrice();
            int quantity = item.getQuantity();
            totalMRP += price * quantity;
            if (item.getDiscountPercent() != null) {
                totalDiscount += (price * item.getDiscountPercent() * quantity) / 100.0;
            }
            double itemShipping = item.getShippingCost() != null ? item.getShippingCost() : 0.0;
            shippingCost = Math.max(shippingCost, itemShipping);
        }
        if (cart.getCouponCode() != null && cart.getCouponCode().equals("NEWUSER")) {
            couponDiscount = totalMRP * 0.1;
        }
        double finalAmount = totalMRP - totalDiscount - couponDiscount + shippingCost + donationAmount;

        // Create order items from cart items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem(
                    item.getProductId().toString(),
                    item.getQuantity(),
                    item.getProductData().getPrice()
            );
            orderItems.add(orderItem);
        }

        // Create and save the order (static payment: "PAID")
        Order order = new Order(userId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
        Order savedOrder = orderRepository.save(order);

        // Clear the cart items after order is placed
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
