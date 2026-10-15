package com.retentix.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomerActivityResponse {

    private Long id;
    private Long customerId;
    private int totalPurchases;
    private LocalDateTime lastPurchaseDate;
    private int totalLogins;
    private LocalDateTime lastLoginDate;
    private int totalSupportRequests;
    private LocalDateTime lastActivityDate;
}