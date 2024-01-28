package com.betheagent.betheagent.properties.service;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.properties.dto.enums.PropertyType;
import com.betheagent.betheagent.properties.dto.enums.Rate;
import com.betheagent.betheagent.properties.dto.enums.Status;
import com.betheagent.betheagent.properties.dto.request.PropertyRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyUpdateRequestDto;
import com.betheagent.betheagent.properties.dto.response.PropertyResponseDto;
import com.betheagent.betheagent.properties.entity.PropertyEntity;
import com.betheagent.betheagent.properties.repository.PropertyRepository;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @Override
    public PropertyResponseDto createProperty(String userId, PropertyRequestDto propertyRequestDto) {

        UserInstance user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with id not found"));

        PropertyEntity propertyEntity = PropertyEntity.builder()
                .authorId(user.getId())
                .name(propertyRequestDto.getName())
                .description(propertyRequestDto.getDescription())
                .propertyType(propertyRequestDto.getPropertyType())
                .numberOfBedrooms(propertyRequestDto.getNumberOfBedrooms())
                .rate(propertyRequestDto.getRate())
                .price(propertyRequestDto.getPrice())
                .numberOfBathrooms(propertyRequestDto.getNumberOfBathrooms())
                .maximumOccupancy(propertyRequestDto.getMaximumOccupancy())
                .amenities(propertyRequestDto.getAmenities())
                .images(propertyRequestDto.getImages())
                .videos(propertyRequestDto.getVideos())
                .availabilityStatus(propertyRequestDto.getAvailabilityStatus())
                .build();

        propertyRepository.save(propertyEntity);
        return mapPropertyEntityToPropertyResponse(propertyEntity);
    }

    @Override
    public PropertyResponseDto findPropertyById(String propertyId) {
      PropertyEntity propertyEntity =  propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(String.format("Property with id: %s not found}", propertyId)));
      return mapPropertyEntityToPropertyResponse(propertyEntity);
    }

    @Override
    public List<PropertyResponseDto> viewAllProperties() {
        return propertyRepository.findAll().stream().map(this::mapPropertyEntityToPropertyResponse).collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponseDto> viewAllPropertiesByUser(String userId) {
       return propertyRepository.findAllPropertiesByUserId(userId).map(this::mapPropertyEntityToPropertyResponse).collect(Collectors.toList());
    }

    @Override
    public PropertyResponseDto updatePropertyById(String propertyId, PropertyUpdateRequestDto propertyUpdateRequestDto) {
        PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(String.format("Property with id: %s not found", propertyId)));

        return null;
    }

    @Override
    public PropertyResponseDto removePropertyById(String propertyId) {
       PropertyEntity property = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(String.format("Property with id: %s not found. Delete operation cannot be performed.", propertyId)));
       property.setAvailabilityStatus(Status.DELETED);
       propertyRepository.save(property);
       return mapPropertyEntityToPropertyResponse(property);
    }

    private PropertyResponseDto mapPropertyEntityToPropertyResponse(PropertyEntity propertyEntity) {

        return PropertyResponseDto.builder()
                .authorId(propertyEntity.getId())
                .name(propertyEntity.getName())
                .description(propertyEntity.getDescription())
                .propertyType(propertyEntity.getPropertyType())
                .numberOfBedrooms(propertyEntity.getNumberOfBedrooms())
                .rate(propertyEntity.getRate())
                .price(propertyEntity.getPrice())
                .numberOfBathrooms(propertyEntity.getNumberOfBathrooms())
                .maximumOccupancy(propertyEntity.getMaximumOccupancy())
                .amenities(propertyEntity.getAmenities())
                .images(propertyEntity.getImages())
                .videos(propertyEntity.getVideos())
                .availabilityStatus(propertyEntity.getAvailabilityStatus())
                .build();

    }
}
