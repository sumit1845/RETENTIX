package com.retentix.service;

import com.retentix.dto.responseDto.ChurnRiskResponse;
import com.retentix.entity.ChurnRisk;
import com.retentix.entity.Feedback;
import com.retentix.entity.Sentiment;
import com.retentix.repository.FeedbackRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChurnRiskService {

    private final FeedbackRepository feedbackRepository;

    public ChurnRiskService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public ChurnRiskResponse assessRisk(Long customerId) {

        // 1. Get customer feedback
        List<Feedback> feedbackList =
                feedbackRepository.findByCustomerId(customerId);

        long totalFeedback = feedbackList.size();

        // 2. Handle customers without feedback
        if (totalFeedback == 0) {
            return ChurnRiskResponse.builder()
                    .customerId(customerId)
                    .totalFeedback(0)
                    .negativeCount(0)
                    .negativePercentage(0)
                    .churnRisk(ChurnRisk.MEDIUM)
                    .recommendation(
                            "Insufficient feedback. Collect customer feedback and activity data."
                    )
                    .build();
        }

        // 3. Count negative feedback
        long negativeCount = feedbackList.stream()
                .filter(feedback ->
                        feedback.getSentiment() == Sentiment.NEGATIVE)
                .count();

        // 4. Calculate negative feedback percentage
        double negativePercentage =
                (negativeCount * 100.0) / totalFeedback;

        // 5. Classify risk
        ChurnRisk churnRisk;
        String recommendation;

        if (negativePercentage >= 50) {
            churnRisk = ChurnRisk.HIGH;
            recommendation =
                    "Contact the customer and resolve their reported problems.";
        } else if (negativePercentage >= 20) {
            churnRisk = ChurnRisk.MEDIUM;
            recommendation =
                    "Follow up with the customer and monitor future feedback.";
        } else {
            churnRisk = ChurnRisk.LOW;
            recommendation =
                    "Maintain engagement and continue collecting feedback.";
        }

        // 6. Return the result
        return ChurnRiskResponse.builder()
                .customerId(customerId)
                .totalFeedback(totalFeedback)
                .negativeCount(negativeCount)
                .negativePercentage(
                        Math.round(negativePercentage * 100.0) / 100.0)
                .churnRisk(churnRisk)
                .recommendation(recommendation)
                .build();
    }
}