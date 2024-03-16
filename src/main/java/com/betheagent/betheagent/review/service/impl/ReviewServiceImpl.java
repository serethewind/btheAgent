package com.betheagent.betheagent.review.service.impl;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import com.betheagent.betheagent.exception.customExceptions.BadRequestException;
import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.ReviewResourceNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.UserResourceNotFoundException;
import com.betheagent.betheagent.properties.entity.PropertyInstance;
import com.betheagent.betheagent.properties.repository.PropertyRepository;
import com.betheagent.betheagent.review.dto.request.ReviewRequestDto;
import com.betheagent.betheagent.review.dto.response.ReviewResponseDto;
import com.betheagent.betheagent.review.entity.ReviewEntity;
import com.betheagent.betheagent.review.repository.ReviewRepository;
import com.betheagent.betheagent.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDto createReview(String propertyId, ReviewRequestDto reviewRequestDto) {
        PropertyInstance propertyEntity = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException("Property not found. Create Review Operation Failed."));
        UserInstance user = userRepository.findById(reviewRequestDto.getUserId()).orElseThrow(() -> new UserResourceNotFoundException("User not found. Create Review Operation Failed"));

        ReviewEntity review = ReviewEntity.builder()
                .userId(reviewRequestDto.getUserId())
                .rating(reviewRequestDto.getRating())
                .propertyId(propertyId)
                .review(reviewRequestDto.getReview())
                .build();

        reviewRepository.save(review);
        return mapReviewEntityToReviewResponseDto(review);
    }

    @Override
    public ReviewResponseDto updateReviewById(String reviewId, ReviewRequestDto reviewRequestDto) {
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewResourceNotFoundException("Review not found. Update review operation failed"));

        if (!review.getUserId().equals(reviewRequestDto.getUserId())) {
            throw new BadRequestException("Only user who created the review can update it. Update review operation failed");
        }

        review.setRating(reviewRequestDto.getRating());
        review.setReview(reviewRequestDto.getReview());
        reviewRepository.save(review);

        return mapReviewEntityToReviewResponseDto(review);
    }

    @Override
    public ReviewResponseDto fetchReviewById(String reviewId) {
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewResourceNotFoundException("Review not found. Fetch review by id operation failed"));
        return mapReviewEntityToReviewResponseDto(review);
    }

    @Override
    public Page<ReviewResponseDto> fetchAllReviewsByPropertyId(String propertyId, Integer pageNo, Integer pageSize) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewRepository.findAllReviewsByPropertyId(propertyId).map(this::mapReviewEntityToReviewResponseDto).toList();
        return createPage(pageNo, pageSize, reviewResponseDtoList);
    }

    private ReviewResponseDto mapReviewEntityToReviewResponseDto(ReviewEntity review) {

        return ReviewResponseDto.builder()
                .reviewId(review.getId())
                .userId(review.getUserId())
                .propertyId(review.getPropertyId())
                .rating(review.getRating())
                .createdAt(review.getUpdatedDate())
                .build();
    }

    private <T> Page<T> createPage(int pageNo, int pageSize, List<T> list) {
        // Handle empty list
        if (list.isEmpty()) {
            return Page.empty();
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        // Handle invalid page number
        int totalPages = (list.size() + pageSize - 1) / pageSize;
        if (pageNo < 0 || pageNo >= totalPages) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        PagedListHolder<T> listHolder = new PagedListHolder<>(list);
        listHolder.setPage(pageNo);
        listHolder.setPageSize(pageSize);

        return new PageImpl<>(listHolder.getPageList(), pageable, list.size());
    }


    private LocalDateTime dateTimeValidator(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            throw new BadRequestException("DateTime cannot be null");
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTime, dateTimeFormatter);
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException("Date pattern is invalid.");
        }
    }
}
