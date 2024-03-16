package com.betheagent.betheagent.review.repository;

import com.betheagent.betheagent.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    @Query("select r from ReviewEntity r where r.propertyId = ?1")
    Stream<ReviewEntity> findAllReviewsByPropertyId(String propertyId);


}
