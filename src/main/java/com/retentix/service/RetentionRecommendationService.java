
package com.retentix.service;

import com.retentix.entity.RetentionRecommendation;
import com.retentix.repository.RetentionRecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class RetentionRecommendationService {

    private final RetentionRecommendationRepository repository;

    public RetentionRecommendationService(
            RetentionRecommendationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RetentionRecommendation createRecommendation(
            Long customerId, String riskLevel) {

        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException(
                    "Customer ID must be a positive number");
        }

        if (riskLevel == null || riskLevel.isBlank()) {
            throw new IllegalArgumentException(
                    "Risk level cannot be empty");
        }

        String normalizedRisk = riskLevel.trim()
                .toUpperCase(Locale.ROOT);

        String action;
        String reason;

        switch (normalizedRisk) {
            case "LOW":
                action = "Maintain customer engagement";
                reason = "Customer has a low churn risk. " +
                        "Continue regular communication and service.";
                break;

            case "MEDIUM":
                action = "Send a personalized offer";
                reason = "Customer has a medium churn risk. " +
                        "Improve engagement through relevant offers.";
                break;

            case "HIGH":
                action = "Contact customer and offer support";
                reason = "Customer has a high churn risk. " +
                        "Take timely action to improve satisfaction.";
                break;

            case "INSUFFICIENT_DATA":
                action = "Collect customer feedback";
                reason = "There is insufficient data to assess churn risk. " +
                        "Collect feedback and customer activity information.";
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported risk level: " + normalizedRisk);
        }

        RetentionRecommendation recommendation =
                RetentionRecommendation.builder()
                        .customerId(customerId)
                        .riskLevel(normalizedRisk)
                        .recommendedAction(action)
                        .reason(reason)
                        .createdAt(LocalDateTime.now())
                        .build();

        return repository.save(recommendation);
    }

    @Transactional(readOnly = true)
    public List<RetentionRecommendation> getRecommendations(
            Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}
