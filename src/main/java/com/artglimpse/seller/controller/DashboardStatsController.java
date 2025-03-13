package com.artglimpse.seller.controller;

import com.artglimpse.seller.model.DashboardStats;
import com.artglimpse.seller.service.DashboardStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/stats")
public class DashboardStatsController {

    @Autowired
    private DashboardStatsService dashboardStatsService;

    // Get the latest stats
    @GetMapping
    public DashboardStats getStats() {
        return dashboardStatsService.getStats();
    }

    // Update stats when an order is placed
    @PostMapping("/order")
    public String updateStatsOnOrder(@RequestParam BigDecimal amount, @RequestParam int productsSold) {
        dashboardStatsService.updateStatsOnOrder(amount, productsSold);
        return "Order stats updated successfully!";
    }

    // Update stats when an order is canceled
    @PostMapping("/order/cancel")
    public String updateStatsOnOrderCancel(@RequestParam BigDecimal amount, @RequestParam int productsSold) {
        dashboardStatsService.updateStatsOnOrderCancel(amount, productsSold);
        return "Order cancellation stats updated successfully!";
    }
}
