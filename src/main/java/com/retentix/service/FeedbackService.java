
package com.retentix.service;

import com.retentix.dto.requestDto.FeedbackRequest;
import com.retentix.dto.responseDto.FeedbackResponse;
import com.retentix.dto.responseDto.SentimentSummaryResponse;
import com.retentix.entity.Feedback;
import com.retentix.entity.FeedbackType;
import com.retentix.entity.Sentiment;
import com.retentix.entity.User;
import com.retentix.repository.FeedbackRepository;
import com.retentix.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final SentimentAnalysis sentimentAnalysis;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            UserRepository userRepository,
            SentimentAnalysis sentimentAnalysis) {

        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.sentimentAnalysis = sentimentAnalysis;
    }

    @Transactional
    public FeedbackResponse submitTextFeedback(
            FeedbackRequest request) {

        // Find the customer
        User customer = userRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found"));

        // Read the feedback text
        String content = request.getContent().trim();

        // Analyze its sentiment
        Sentiment sentiment =
                sentimentAnalysis.analyze(content);

        // Create the feedback record
        Feedback feedback = Feedback.builder()
                .customer(customer)
                .feedbackType(FeedbackType.TEXT)
                .content(content)
                .sentiment(sentiment)
                .build();

        // Save it to PostgreSQL
        Feedback saved =
                feedbackRepository.save(feedback);

        // Return the response
        return FeedbackResponse.builder()
                .id(saved.getId())
                .customerId(customer.getId())
                .feedbackType(saved.getFeedbackType())
                .content(saved.getContent())
                .sentiment(saved.getSentiment())
                .submittedAt(saved.getSubmittedAt())
                .build();
    }

    public List<FeedbackResponse> getFeedbackByCustomer(Long customerId) {

        // Retrieve feedback from the database
        List<Feedback> feedbackList =
                feedbackRepository.findByCustomerId(customerId);

        // Convert Feedback entities into response DTOs
        return feedbackList.stream()
                .map(feedback -> FeedbackResponse.builder()
                        .id(feedback.getId())
                        .customerId(feedback.getCustomer().getId())
                        .feedbackType(feedback.getFeedbackType())
                        .content(feedback.getContent())
                        .sentiment(feedback.getSentiment())
                        .submittedAt(feedback.getSubmittedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public SentimentSummaryResponse getSentimentSummary(Long customerId) {

        List<Feedback> feedbackList =
                feedbackRepository.findByCustomerId(customerId);

        long positive = feedbackList.stream()
                .filter(f -> f.getSentiment() == Sentiment.POSITIVE)
                .count();

        long negative = feedbackList.stream()
                .filter(f -> f.getSentiment() == Sentiment.NEGATIVE)
                .count();

        long neutral = feedbackList.stream()
                .filter(f -> f.getSentiment() == Sentiment.NEUTRAL)
                .count();

        return SentimentSummaryResponse.builder()
                .customerId(customerId)
                .totalFeedback(feedbackList.size())
                .positiveCount(positive)
                .negativeCount(negative)
                .neutralCount(neutral)
                .build();
    }
}
