package com.betheagent.betheagent.properties.service.impl;


import com.betheagent.betheagent.properties.dto.enums.Rate;
import com.betheagent.betheagent.properties.dto.enums.Status;
import com.betheagent.betheagent.exception.customExceptions.BadRequestException;
import com.betheagent.betheagent.properties.entity.PropertyInstance;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PropertyFilterService {

    public Specification<PropertyInstance> findByUserId(Optional<String> userId) {
        return (root, query, criteriaBuilder) -> userId.map(id -> criteriaBuilder.equal(root.get("authorId"), id)).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> findByPropertyName(Optional<String> propertyName) {
        return (root, query, criteriaBuilder) -> propertyName.map(property -> criteriaBuilder.like(root.get("name"), property)).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> findByPropertyType(Optional<String> propertyType) {
        return (root, query, criteriaBuilder) -> propertyType.map(property -> {
                    try {
                        return criteriaBuilder.equal(root.get("propertyType"), Status.valueOf(property.toUpperCase()));
                    } catch (IllegalArgumentException exception) {
                        exception.printStackTrace();
                        throw new BadRequestException("Invalid PropertyType declared");
                    }
                }

        ).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> findByStatus(Optional<String> status) {
        return (root, query, criteriaBuilder) ->
                status.map(statusOfProperty -> {
                    try {
                        return criteriaBuilder.equal(root.get("availabilityStatus"), Status.valueOf(statusOfProperty.toUpperCase()));
                    } catch (IllegalArgumentException exception) {
                        exception.printStackTrace();
                        throw new BadRequestException("Wrong Status Type Declared");
                    }
                }).orElseGet((criteriaBuilder::conjunction));
    }

    public Specification<PropertyInstance> findByRate(Optional<String> rate) {
        return (root, query, criteriaBuilder) -> rate.map(rateInstance -> {
            try {
                return criteriaBuilder.equal(root.get("rate"), Rate.valueOf(rateInstance.toUpperCase()));
            } catch (IllegalArgumentException exception) {
                exception.printStackTrace();
                throw new BadRequestException("Wrong Rate Type Declared");
            }
        }).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> findByNumberOfBedrooms(Optional<Integer> numberOfBedrooms) {
        return (root, query, criteriaBuilder) -> numberOfBedrooms.map(property -> criteriaBuilder.equal(root.get("numberOfBedrooms"), property)).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> findByAmenities(Optional<String> amenities) {
        return (root, query, criteriaBuilder) -> amenities.map(property -> criteriaBuilder.like(root.get("amenities"), property)).orElseGet(criteriaBuilder::conjunction);
    }

    public Specification<PropertyInstance> uploadedWithinDateRange(Optional<Instant> startDate, Optional<Instant> endDate) {
        return (root, query, criteriaBuilder) ->
                startDate.flatMap(startOfRange ->
                        endDate.map(endOfRange ->
                                criteriaBuilder.between(root.get("createdDate"), startOfRange, endOfRange)
                        )
                ).orElseGet(criteriaBuilder::conjunction);
    }

    //Optional<String> userId, Optional<String> propertyName, Optional<String> propertyType, Optional<String> status, Optional<String> rate, Optional<Integer>, Optional<Integer> numberOfBedrooms, Optional <String> amenities, Optional<Instant> startDate, Optional<Instant> endDate

    public Specification<PropertyInstance> filterPropertyAndFindBySpecification(
            Optional<String> userId,
            Optional<String> propertyName,
            Optional<String> propertyType,
            Optional<String> status,
            Optional<String> rate,
            Optional<Integer> numberOfBedrooms,
            Optional<String> amenities,
            Optional<Instant> startDate,
            Optional<Instant> endDate) {

        Specification<PropertyInstance> specification = Specification
                .where(findByUserId(userId))
                .and(findByPropertyName(propertyName))
                .and(findByPropertyType(propertyType))
                .and(findByStatus(status))
                .and(findByRate(rate))
                .and(findByNumberOfBedrooms(numberOfBedrooms))
                .and(findByAmenities(amenities))
                .and(uploadedWithinDateRange(startDate, endDate));

        return specification;
    }

/**
 * name -> propertyName contains not equals
 * numberOfBedroom -> numberOfBedroom equals
 * Rate -> Rate equals
 * amenities -> equals
 * AvailabilityStatus -> equals
 * price .. greater than, less than, price range
 */
}
