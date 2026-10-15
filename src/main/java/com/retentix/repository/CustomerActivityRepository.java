package com.retentix.repository;

import com.retentix.entity.CustomerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerActivityRepository
        extends JpaRepository<CustomerActivity, Long> {

    Optional<CustomerActivity> findByCustomerId(Long customerId);
}