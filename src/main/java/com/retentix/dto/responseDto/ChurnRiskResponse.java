package com.retentix.dto.responseDto;

import com.retentix.entity.ChurnRisk;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChurnRiskResponse {

    private Long customerId;
    private long totalFeedback;
    private long negativeCount;
    private double negativePercentage;
    private ChurnRisk churnRisk;
    private String recommendation;
}