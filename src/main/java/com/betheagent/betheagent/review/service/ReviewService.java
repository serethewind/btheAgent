package com.betheagent.betheagent.review.service;

import com.betheagent.betheagent.review.dto.request.ReviewRequestDto;
import com.betheagent.betheagent.review.dto.response.ReviewResponseDto;
import org.springframework.data.domain.Page;

public interface ReviewService {
    ReviewResponseDto createReview(String propertyId, ReviewRequestDto reviewRequestDto);

    ReviewResponseDto updateReviewById(String reviewId, ReviewRequestDto reviewRequestDto);

    ReviewResponseDto fetchReviewById(String reviewId);

    Page<ReviewResponseDto> fetchAllReviewsByPropertyId(String propertyId, Integer pageNo, Integer pageSize);
}
