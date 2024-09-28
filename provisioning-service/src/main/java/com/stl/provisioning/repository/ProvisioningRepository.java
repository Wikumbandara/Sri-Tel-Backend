package com.stl.provisioning.repository;

import com.stl.provisioning.entity.Provisioning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvisioningRepository extends JpaRepository<Provisioning, Long> {
    Optional<Object> findByServiceIdAndUserId(Long serviceId, Long userId);

    Iterable<Optional<Object>> findByUserId(Long userId);
}