package com.betheagent.betheagent.properties.service;

import com.betheagent.betheagent.properties.dto.request.PropertyRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyUpdateRequestDto;
import com.betheagent.betheagent.properties.dto.response.PropertyResponseDto;

import java.util.List;

public interface PropertyService {
    PropertyResponseDto createProperty(String userId, PropertyRequestDto propertyRequestDto);

    PropertyResponseDto findPropertyById(String propertyId);

    List<PropertyResponseDto> viewAllProperties();

    List<PropertyResponseDto> viewAllPropertiesByUser(String userId);

    PropertyResponseDto updatePropertyById(String propertyId, PropertyUpdateRequestDto propertyUpdateRequestDto);

    PropertyResponseDto removePropertyById(String propertyId);
}
