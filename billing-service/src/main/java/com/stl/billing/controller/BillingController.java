package com.stl.billing.controller;

import com.stl.billing.entity.Billing;
import com.stl.billing.service.BillingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/generate/{id}")
    public void generateMonthlyBill(@PathVariable Long id) {
        billingService.generateMonthlyBill(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Billing>> getBillsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getBillsByUserId(id));
    }
}
