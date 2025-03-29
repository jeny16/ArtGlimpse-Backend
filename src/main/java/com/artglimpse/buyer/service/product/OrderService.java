package com.artglimpse.buyer.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artglimpse.buyer.model.product.Cart;
import com.artglimpse.buyer.model.product.CartItem;
import com.artglimpse.buyer.model.product.Order;
import com.artglimpse.buyer.model.product.OrderItem;
import com.artglimpse.buyer.model.profile.Address;
import com.artglimpse.buyer.repository.product.CartRepository;
import com.artglimpse.buyer.repository.product.OrderRepository;
import com.artglimpse.buyer.repository.product.ProductRepository;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(ObjectId userId, Cart cart, Address shippingAddress) {
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
                    item.getProductId(), // Already an ObjectId
                    item.getQuantity(),
                    item.getProductData().getPrice());
            orderItems.add(orderItem);
        }

        // Create and save the order
        Order order = new Order(userId, orderItems, finalAmount, "PAID", shippingAddress, new Date());
        Order savedOrder = orderRepository.save(order);

        // Populate each OrderItem with full product data
        savedOrder.getItems().forEach(item -> {
            productRepository.findById(item.getProductId().toString())
                    .ifPresent(item::setProductData);
        });

        // Clear the cart items after placing the order
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getOrdersByUser(ObjectId userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        // Optionally populate product data for each order item:
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                productRepository.findById(item.getProductId().toString())
                        .ifPresent(item::setProductData);
            });
        });
        return orders;
    }
}
