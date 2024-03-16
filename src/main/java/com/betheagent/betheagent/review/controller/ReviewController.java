package com.betheagent.betheagent.review.controller;

import com.betheagent.betheagent.review.dto.request.ReviewRequestDto;
import com.betheagent.betheagent.review.dto.response.ReviewResponseDto;
import com.betheagent.betheagent.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/btheagent/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{propertyId}/leave-review")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("propertyId") String propertyId, @RequestBody ReviewRequestDto reviewRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(propertyId, reviewRequestDto));
    }

    @PutMapping("/{reviewId}/update-review")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable("reviewId") String reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReviewById(reviewId, reviewRequestDto));
    }

    @GetMapping("fetch-review")
    public ResponseEntity<ReviewResponseDto> fetchReviewById(@RequestParam("reviewId") String reviewId){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.fetchReviewById(reviewId));
    }

    @GetMapping("{propertyId}/fetch-reviews")
    public ResponseEntity<Page<ReviewResponseDto>> fetchAllReviewsByPropertyId(
            @PathVariable("propertyId") String propertyId,
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.fetchAllReviewsByPropertyId(propertyId, pageNo, pageSize));
    }



}
