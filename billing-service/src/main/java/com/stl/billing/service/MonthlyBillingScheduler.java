package com.stl.billing.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MonthlyBillingScheduler {
    private final BillingService billingService;

    // Schedule to generate monthly bill for all users
    @Scheduled(cron = "0 0 0 L * ?")
    public void generateMonthlyBill() {
        // Get all active users
        List<Long> userIds = billingService.getAllUserIds();

        // Generate monthly bill for each user
        for (Long userId : userIds) {
            billingService.generateMonthlyBill(userId);
        }
    }

}
