package com.retentix.controller;

import com.retentix.dto.requestDto.CustomerActivityRequest;
import com.retentix.dto.responseDto.CustomerActivityResponse;
import com.retentix.service.CustomerActivityService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity")
public class CustomerActivityController {

    private final CustomerActivityService activityService;

    public CustomerActivityController(
            CustomerActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<CustomerActivityResponse> saveActivity(
            @Valid @RequestBody CustomerActivityRequest request) {

        CustomerActivityResponse response =
                activityService.saveActivity(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerActivityResponse> getActivity(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                activityService.getActivity(customerId));
    }
}