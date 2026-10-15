
package com.retentix.controller;

import com.retentix.entity.RetentionRecommendation;
import com.retentix.service.RetentionRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retention/recommendations")
public class RetentionRecommendationController {

    private final RetentionRecommendationService service;

    public RetentionRecommendationController(
            RetentionRecommendationService service) {
        this.service = service;
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<RetentionRecommendation> createRecommendation(
            @PathVariable Long customerId,
            @RequestParam String riskLevel) {

        RetentionRecommendation recommendation =
                service.createRecommendation(customerId, riskLevel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recommendation);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RetentionRecommendation>> getRecommendations(
            @PathVariable Long customerId) {

        List<RetentionRecommendation> recommendations =
                service.getRecommendations(customerId);

        return ResponseEntity.ok(recommendations);
    }
}
