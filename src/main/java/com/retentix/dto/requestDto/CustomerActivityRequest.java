package com.retentix.dto.requestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerActivityRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Min(value = 0, message = "Total purchases cannot be negative")
    private int totalPurchases;

    private LocalDateTime lastPurchaseDate;

    @Min(value = 0, message = "Total logins cannot be negative")
    private int totalLogins;

    private LocalDateTime lastLoginDate;

    @Min(value = 0, message = "Support requests cannot be negative")
    private int totalSupportRequests;

    private LocalDateTime lastActivityDate;
}