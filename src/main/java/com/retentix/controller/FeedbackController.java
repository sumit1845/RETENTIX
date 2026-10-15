package com.retentix.controller;

import com.retentix.dto.requestDto.FeedbackRequest;
import com.retentix.dto.responseDto.FeedbackResponse;
import com.retentix.dto.responseDto.SentimentSummaryResponse;
import com.retentix.service.FeedbackService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/text")
    public ResponseEntity<FeedbackResponse> submitTextFeedback(
            @Valid @RequestBody FeedbackRequest request) {

        FeedbackResponse response =
                feedbackService.submitTextFeedback(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<FeedbackResponse>> getCustomerFeedback(
            @PathVariable Long customerId) {

        List<FeedbackResponse> feedbackList =
                feedbackService.getFeedbackByCustomer(customerId);

        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/customer/{customerId}/sentiment-summary")
    public ResponseEntity<SentimentSummaryResponse> getSentimentSummary(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                feedbackService.getSentimentSummary(customerId)
        );
    }
}
