package com.retentix.repository;

import com.retentix.entity.ChurnPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChurnPredictionRepository
        extends JpaRepository<ChurnPrediction, Long> {

    List<ChurnPrediction> findByCustomerId(Long customerId);

    List<ChurnPrediction> findByRiskLevel(String riskLevel);
}