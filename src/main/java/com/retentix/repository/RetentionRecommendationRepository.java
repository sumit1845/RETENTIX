package com.retentix.repository;

import com.retentix.entity.RetentionRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetentionRecommendationRepository
        extends JpaRepository<RetentionRecommendation, Long> {

    List<RetentionRecommendation> findByCustomerId(Long customerId);

    List<RetentionRecommendation> findByRiskLevel(String riskLevel);
}