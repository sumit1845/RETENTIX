
package com.retentix.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class FeedbackRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Feedback content is required")
    @Size(max = 10000,
            message = "Feedback must not exceed 10000 characters")
    private String content;
}
