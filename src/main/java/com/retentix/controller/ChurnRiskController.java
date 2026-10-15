package com.retentix.controller;

import com.retentix.dto.responseDto.ChurnRiskResponse;
import com.retentix.service.ChurnRiskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/churn")
public class ChurnRiskController {

    private final ChurnRiskService churnRiskService;

    public ChurnRiskController(ChurnRiskService churnRiskService) {
        this.churnRiskService = churnRiskService;
    }

    @GetMapping("/customer/{customerId}/risk")
    public ResponseEntity<ChurnRiskResponse> assessCustomerRisk(
            @PathVariable Long customerId) {

        ChurnRiskResponse response =
                churnRiskService.assessRisk(customerId);

        return ResponseEntity.ok(response);
    }
}