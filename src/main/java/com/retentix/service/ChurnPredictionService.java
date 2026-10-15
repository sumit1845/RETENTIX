package com.retentix.service;

import com.retentix.dto.requestDto.ChurnPredictionRequest;
import com.retentix.entity.ChurnPrediction;
import com.retentix.repository.ChurnPredictionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ChurnPredictionService {

    private final ChurnPredictionRepository churnPredictionRepository;

    public ChurnPredictionService(
            ChurnPredictionRepository churnPredictionRepository) {
        this.churnPredictionRepository = churnPredictionRepository;
    }

    @Transactional
    public ChurnPrediction predictChurn(ChurnPredictionRequest request) {

        double riskScore = 0.0;

        // 1. Check customer tenure
        if (request.getTenureMonths() <= 3) {
            riskScore += 25;
        } else if (request.getTenureMonths() <= 12) {
            riskScore += 15;
        }

        // 2. Check customer complaints
        if (request.getComplaintsCount() >= 4) {
            riskScore += 25;
        } else if (request.getComplaintsCount() >= 2) {
            riskScore += 15;
        }

        // 3. Check customer engagement
        if (request.getEngagementScore() < 30) {
            riskScore += 25;
        } else if (request.getEngagementScore() < 60) {
            riskScore += 15;
        }

        // 4. Check payment delays
        if (request.getPaymentDelayDays() >= 15) {
            riskScore += 25;
        } else if (request.getPaymentDelayDays() >= 7) {
            riskScore += 15;
        }

        // Convert risk score into a probability
        double churnProbability = riskScore / 100.0;

        // Determine risk level
        String riskLevel;

        if (churnProbability >= 0.70) {
            riskLevel = "HIGH";
        } else if (churnProbability >= 0.40) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        // Prepare prediction entity
        ChurnPrediction prediction = ChurnPrediction.builder()
                .customerId(request.getCustomerId())
                .churnProbability(churnProbability)
                .riskLevel(riskLevel)
                .predictionReason(
                        "Risk assessed using tenure, complaints, engagement and payment delays.")
                .predictedAt(LocalDateTime.now())
                .build();

        // Save prediction in PostgreSQL
        return churnPredictionRepository.save(prediction);
    }
}