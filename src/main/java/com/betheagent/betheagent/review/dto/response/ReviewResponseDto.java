package com.betheagent.betheagent.review.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewResponseDto {

    private String reviewId;
    private String userId;
    private String propertyId;
    private String review;
    private int rating;
    private LocalDateTime createdAt;
}
