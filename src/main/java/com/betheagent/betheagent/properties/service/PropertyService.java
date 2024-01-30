package com.betheagent.betheagent.properties.service;

import com.betheagent.betheagent.properties.dto.request.PropertyRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyUpdateRequestDto;
import com.betheagent.betheagent.properties.dto.response.PropertyResponseDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface PropertyService {
    PropertyResponseDto createProperty(String userId, PropertyRequestDto propertyRequestDto);

    PropertyResponseDto findPropertyById(String propertyId);

    Page<PropertyResponseDto> viewAllProperties(Integer pageNo, Integer pageSize, String sortDirection, String byColumn);

    Page<PropertyResponseDto> viewAllPropertiesByUser(String userId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn);

    PropertyResponseDto updatePropertyById(String propertyId, PropertyUpdateRequestDto propertyUpdateRequestDto);

    PropertyResponseDto removePropertyById(String propertyId);

    Page<PropertyResponseDto> filterPropertiesWithQueries(String userId, String propertyName, String propertyType, String status, String rate, Integer numberOfBedroom, String amenities, Instant recentDate, Instant presentDate, Integer pageNo, Integer pageSize, String sortDirection, String byColumn);


//    recentDate, presentDate,  pageNo, pageSize, sortDirection, byColumn
}
