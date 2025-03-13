package com.artglimpse.seller.service;

import com.artglimpse.seller.model.DashboardStats;
import com.artglimpse.seller.repository.DashboardStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class DashboardStatsService {

    @Autowired
    private DashboardStatsRepository dashboardStatsRepository;

    // Get the stats document (ensure one exists)
    private DashboardStats getOrCreateStats() {
        Optional<DashboardStats> statsOpt = dashboardStatsRepository.findAll().stream().findFirst();
        return statsOpt.orElseGet(() -> {
            DashboardStats newStats = new DashboardStats();
            dashboardStatsRepository.save(newStats);
            return newStats;
        });
    }

    // Update stats on order placement
    public void updateStatsOnOrder(BigDecimal orderAmount, int productsSold) {
        DashboardStats stats = getOrCreateStats();
        stats.setTotalRevenue(stats.getTotalRevenue().add(orderAmount));
        stats.setTotalOrders(stats.getTotalOrders() + 1);
        stats.setProductsSold(stats.getProductsSold() + productsSold);
        stats.setConversionRate((double) stats.getTotalOrders() / stats.getVisitors() * 100);
        dashboardStatsRepository.save(stats);
    }

    // Update stats on order cancellation
    public void updateStatsOnOrderCancel(BigDecimal orderAmount, int productsSold) {
        DashboardStats stats = getOrCreateStats();
        stats.setTotalRevenue(stats.getTotalRevenue().subtract(orderAmount));
        stats.setTotalOrders(stats.getTotalOrders() - 1);
        stats.setProductsSold(stats.getProductsSold() - productsSold);
        stats.setConversionRate((double) stats.getTotalOrders() / stats.getVisitors() * 100);
        dashboardStatsRepository.save(stats);
    }

    // Fetch the latest dashboard stats
    public DashboardStats getStats() {
        return getOrCreateStats();
    }
}
