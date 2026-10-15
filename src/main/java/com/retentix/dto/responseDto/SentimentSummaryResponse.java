package com.retentix.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentimentSummaryResponse {

    private Long customerId;
    private long totalFeedback;
    private long positiveCount;
    private long negativeCount;
    private long neutralCount;
}
