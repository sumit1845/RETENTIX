package com.retentix.controller;

import com.retentix.dto.requestDto.ChurnPredictionRequest;
import com.retentix.entity.ChurnPrediction;
import com.retentix.repository.ChurnPredictionRepository;
import com.retentix.service.ChurnPredictionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/churn")
public class ChurnPredictionController {

    private final ChurnPredictionService churnPredictionService;
    private final ChurnPredictionRepository churnPredictionRepository;

    public ChurnPredictionController(
            ChurnPredictionService churnPredictionService,
            ChurnPredictionRepository churnPredictionRepository) {

        this.churnPredictionService = churnPredictionService;
        this.churnPredictionRepository = churnPredictionRepository;
    }

    // Predict customer churn
    @PostMapping("/predict")
    public ResponseEntity<ChurnPrediction> predictChurn(
            @Valid @RequestBody ChurnPredictionRequest request) {

        ChurnPrediction prediction =
                churnPredictionService.predictChurn(request);

        return ResponseEntity.ok(prediction);
    }

    // Get prediction history for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ChurnPrediction>> getCustomerPredictions(
            @PathVariable Long customerId) {

        List<ChurnPrediction> predictions =
                churnPredictionRepository.findByCustomerId(customerId);

        return ResponseEntity.ok(predictions);
    }

    // Get all high-risk predictions
    @GetMapping("/high-risk")
    public ResponseEntity<List<ChurnPrediction>> getHighRiskPredictions() {

        List<ChurnPrediction> predictions =
                churnPredictionRepository.findByRiskLevel("HIGH");

        return ResponseEntity.ok(predictions);
    }
}