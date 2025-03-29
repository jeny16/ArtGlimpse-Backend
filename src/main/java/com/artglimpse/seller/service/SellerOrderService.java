package com.artglimpse.seller.service;

import com.artglimpse.seller.model.OrdersList;
import com.artglimpse.seller.repository.SellerOrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerOrderService {

    @Autowired
    private SellerOrderRepository sellerOrderRepository;

    // Query orders using sellerId as a String.
    public List<OrdersList> getRecentOrdersForSeller(String sellerId, int page, int size) {
        Page<OrdersList> ordersPage = sellerOrderRepository.findBySellerIdOrderByCreatedAtDesc(sellerId, PageRequest.of(page, size));
        return ordersPage.getContent();
    }

    public OrdersList updateOrderStatus(String orderId, String newStatus) {
        ObjectId oid = new ObjectId(orderId);
        Optional<OrdersList> orderOptional = sellerOrderRepository.findById(oid);
        if (!orderOptional.isPresent()) {
            throw new RuntimeException("Order not found");
        }
        OrdersList order = orderOptional.get();
        order.setPaymentStatus(newStatus);
        return sellerOrderRepository.save(order);
    }
}
