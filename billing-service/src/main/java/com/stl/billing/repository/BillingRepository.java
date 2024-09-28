package com.stl.billing.repository;

import com.stl.billing.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByUserId(Long userId);
}