package com.retentix.dto.requestDto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChurnPredictionRequest {

    @NotNull(message = "Customer ID is required")
    @Min(value = 1, message = "Customer ID must be positive")
    private Long customerId;

    @NotNull(message = "Monthly charges are required")
    @DecimalMin(value = "0.0", message = "Monthly charges cannot be negative")
    private Double monthlyCharges;

    @NotNull(message = "Tenure is required")
    @Min(value = 0, message = "Tenure cannot be negative")
    private Integer tenureMonths;

    @NotNull(message = "Number of complaints is required")
    @Min(value = 0, message = "Complaints cannot be negative")
    private Integer complaintsCount;

    @NotNull(message = "Engagement score is required")
    @DecimalMin(value = "0.0", message = "Engagement score must be at least 0")
    @DecimalMax(value = "100.0", message = "Engagement score cannot exceed 100")
    private Double engagementScore;

    @NotNull(message = "Payment delay is required")
    @Min(value = 0, message = "Payment delay cannot be negative")
    private Integer paymentDelayDays;
}