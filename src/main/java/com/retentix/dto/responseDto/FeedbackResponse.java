
package com.retentix.dto.responseDto;

import com.retentix.entity.FeedbackType;
import com.retentix.entity.Sentiment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FeedbackResponse {

    private Long id;
    private Long customerId;
    private FeedbackType feedbackType;
    private String content;
    private Sentiment sentiment;
    private LocalDateTime submittedAt;
}
