package com.stl.provisioning.repository;

import com.stl.provisioning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByAccNumber(String accNumber);

    Optional<Object> findByEmail(String username);
}