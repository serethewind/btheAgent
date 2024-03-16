package com.betheagent.betheagent.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequestDto {
    @NotEmpty(message = "UserId should not be empty")
    private String userId;
    private String review;
    @Min(1)
    @Max(5)
    private int rating;
}
