package com.betheagent.betheagent.properties.controller;

import com.betheagent.betheagent.properties.dto.request.PropertyRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyUpdateRequestDto;
import com.betheagent.betheagent.properties.dto.response.PropertyResponseDto;
import com.betheagent.betheagent.properties.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("v1/api/btheagent/properties")
@RequiredArgsConstructor
@EnableMethodSecurity
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("{userId}/create")
    public ResponseEntity<PropertyResponseDto> createProperty(@PathVariable("userId") String userId, @RequestBody PropertyRequestDto propertyRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(userId, propertyRequestDto));
    }

    @GetMapping("viewProperty/{propertyId}")
    public ResponseEntity<PropertyResponseDto> viewSingleProperty(@PathVariable("propertyId") String propertyId) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyById(propertyId));
    }

    @GetMapping("all-properties")
    public ResponseEntity<Page<PropertyResponseDto>> viewAllProperties(
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.viewAllProperties(pageNo, pageSize, sortDirection, byColumn));
    }

    @GetMapping("{userId}/properties")
    public ResponseEntity<Page<PropertyResponseDto>> viewAllPropertiesByUser(
            @PathVariable("userId") String userId,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.viewAllPropertiesByUser(userId, pageNo, pageSize, sortDirection, byColumn));
    }


    @PutMapping("{propertyId}/update")
    public ResponseEntity<PropertyResponseDto> updateSingleProperty(@PathVariable("propertyId") String propertyId, @RequestBody PropertyUpdateRequestDto propertyUpdateRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updatePropertyById(propertyId, propertyUpdateRequestDto));
    }

    @DeleteMapping("{propertyId}/remove")
    public ResponseEntity<PropertyResponseDto> softDeleteSinglePropertyInstance(@PathVariable("propertyId") String propertyId) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.removePropertyById(propertyId));
    }

    /**
     * Filters cohorts based on various parameters such as userId, status, rate, start and end dates.
     *
     * @param userId      The unique identifier of the organization (optional).
     * @param status      The status of cohorts (optional).
     * @param rate  The unique identifier of the cohort (optional).
     * @param recentDate  The beginning of the start date range (optional).
     * @param presentDate The end of the start date range (optional).
     * @return A ResponseEntity containing a page of CreateCohortResponse based on the applied filters.
     */
    @GetMapping("filter-properties")
    public ResponseEntity<Page<PropertyResponseDto>> filterCohorts(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String propertyName,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String rate,
            @RequestParam(required = false) Integer numberOfBedroom,
            @RequestParam(required = false) String amenities,
//date is appearing as instant, but it will most likely be brought in as a string
            @RequestParam(required = false) Instant recentDate,
            @RequestParam(required = false) Instant presentDate,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn
    ) {
        return ResponseEntity.ok(propertyService.filterPropertiesWithQueries(userId, propertyName, propertyType, status, rate, numberOfBedroom, amenities, recentDate, presentDate, pageNo, pageSize, sortDirection, byColumn));

//    Sorting operations for properties by status, by date etc...
    }
}
