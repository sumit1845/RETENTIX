package com.retentix.service;

import com.retentix.dto.requestDto.CustomerActivityRequest;
import com.retentix.dto.responseDto.CustomerActivityResponse;
import com.retentix.entity.CustomerActivity;
import com.retentix.entity.User;
import com.retentix.repository.CustomerActivityRepository;
import com.retentix.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CustomerActivityService {

    private final CustomerActivityRepository activityRepository;
    private final UserRepository userRepository;

    public CustomerActivityService(
            CustomerActivityRepository activityRepository,
            UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CustomerActivityResponse saveActivity(
            CustomerActivityRequest request) {

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        CustomerActivity activity = activityRepository
                .findByCustomerId(customer.getId())
                .orElseGet(() -> CustomerActivity.builder()
                        .customer(customer)
                        .build());

        activity.setTotalPurchases(request.getTotalPurchases());
        activity.setLastPurchaseDate(request.getLastPurchaseDate());
        activity.setTotalLogins(request.getTotalLogins());
        activity.setLastLoginDate(request.getLastLoginDate());
        activity.setTotalSupportRequests(
                request.getTotalSupportRequests());

        activity.setLastActivityDate(
                request.getLastActivityDate() != null
                        ? request.getLastActivityDate()
                        : LocalDateTime.now());

        CustomerActivity saved = activityRepository.save(activity);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerActivityResponse getActivity(Long customerId) {

        CustomerActivity activity = activityRepository
                .findByCustomerId(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Activity not found for customer"));

        return toResponse(activity);
    }

    private CustomerActivityResponse toResponse(
            CustomerActivity activity) {

        return CustomerActivityResponse.builder()
                .id(activity.getId())
                .customerId(activity.getCustomer().getId())
                .totalPurchases(activity.getTotalPurchases())
                .lastPurchaseDate(activity.getLastPurchaseDate())
                .totalLogins(activity.getTotalLogins())
                .lastLoginDate(activity.getLastLoginDate())
                .totalSupportRequests(
                        activity.getTotalSupportRequests())
                .lastActivityDate(activity.getLastActivityDate())
                .build();
    }
}