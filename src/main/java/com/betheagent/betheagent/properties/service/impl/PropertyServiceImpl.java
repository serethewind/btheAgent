package com.betheagent.betheagent.properties.service.impl;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.properties.dto.enums.DirectionOfSorting;
import com.betheagent.betheagent.properties.dto.enums.Status;
import com.betheagent.betheagent.properties.dto.request.PageRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyRequestDto;
import com.betheagent.betheagent.properties.dto.request.PropertyUpdateRequestDto;
import com.betheagent.betheagent.properties.dto.response.PropertyResponseDto;
import com.betheagent.betheagent.properties.entity.PropertyEntity;
import com.betheagent.betheagent.properties.repository.PropertyRepository;
import com.betheagent.betheagent.properties.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    private final PropertyFilterService propertyFilterService;

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
        PropertyEntity propertyEntity = propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(String.format("Property with id: %s not found}", propertyId)));
        return mapPropertyEntityToPropertyResponse(propertyEntity);
    }

    @Override
    public Page<PropertyResponseDto> viewAllProperties(Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
        PageRequestDto pageRequestDto = mapRequestParamToPageRequestDto(pageNo, pageSize, sortDirection, byColumn);
        List<PropertyResponseDto> propertyList = propertyRepository.findAll().stream().map(this::mapPropertyEntityToPropertyResponse).collect(Collectors.toList());
        return createPage(pageNo, pageSize, propertyList);
    }

    @Override
    public Page<PropertyResponseDto> viewAllPropertiesByUser(String userId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
        PageRequestDto pageRequestDto = mapRequestParamToPageRequestDto(pageNo, pageSize, sortDirection, byColumn);
        List<PropertyResponseDto> propertyList = propertyRepository.findAllPropertiesByUserId(userId).map(this::mapPropertyEntityToPropertyResponse).toList();
        return createPage(pageNo, pageSize, propertyList);
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

    @Override
    public Page<PropertyResponseDto> filterPropertiesWithQueries(String userId, String propertyName, String propertyType, String status, String rate, Integer numberOfBedroom, String amenities, Instant recentDate, Instant presentDate, Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {

        PageRequestDto pageRequestDto = mapRequestParamToPageRequestDto(pageNo, pageSize, sortDirection, byColumn);


        List<PropertyResponseDto> propertyResponseDtoList = propertyRepository.findAll(
                propertyFilterService.filterPropertyAndFindBySpecification(
                        Optional.ofNullable(userId),
                        Optional.ofNullable(propertyName),
                        Optional.ofNullable(propertyType),
                        Optional.ofNullable(status),
                        Optional.ofNullable(rate),
                        Optional.ofNullable(numberOfBedroom),
                        Optional.ofNullable(amenities),
                        Optional.ofNullable(recentDate),
                        Optional.ofNullable(presentDate)
                )
        ).stream().map(this::mapPropertyEntityToPropertyResponse).toList();

        return createPage(pageNo, pageSize, propertyResponseDtoList);
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

    private PageRequestDto mapRequestParamToPageRequestDto(Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
        DirectionOfSorting sorting = getDirectionOfSorting(sortDirection);

        return new PageRequestDto(pageNo, pageSize, sorting.equals(DirectionOfSorting.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC, byColumn);
    }


    /**
     * Converts a list to a {@link PagedListHolder} with pagination settings.
     *
     * @param listFromQuery  The list to be converted.
     * @param pageRequestDto The page request parameters.
     * @param <T>            The type of elements in the list.
     * @return A {@link PagedListHolder} containing the specified list.
     */
    private <T> PagedListHolder<T> convertListToPageListHolder(List<T> listFromQuery, PageRequestDto pageRequestDto) {
        PagedListHolder<T> pagedListHolder = new PagedListHolder<>(listFromQuery);

        pagedListHolder.setPage(pageRequestDto.getPageNo());
        pagedListHolder.setPageSize(pageRequestDto.getPageSize());

        return pagedListHolder;
    }

    /**
     * Defines a comparator, sorts the list, and converts it to a paginated {@link Page}.
     *
     * @param pagedListHolder The paged list holder.
     * @param pageRequestDto  The page request parameters.
     * @param pageTotal       The total number of elements.
     * @param <T>             The type of elements in the list.
     * @return A paginated {@link Page} based on the sorted list.
     */
    private <T> Page<T> defineComparatorAndConvertToPage(PagedListHolder<T> pagedListHolder, PageRequestDto pageRequestDto, long pageTotal) {
        List<T> pageSlice = pagedListHolder.getPageList();
        log.info("check");
//        PropertyComparator.sort(pageSlice, new MutableSortDefinition(pageRequestDto.getSortByColumn(), true, pageRequestDto.getSortDirection().isAscending()));
        log.info("testing mic");
        return new PageImpl<>(pageSlice, pageRequestDto.getPageable(), pageTotal);
    }

    private static DirectionOfSorting getDirectionOfSorting(String sortDirection) {
        DirectionOfSorting sorting;

        if (sortDirection == null) {
            sorting = DirectionOfSorting.ASC;
        } else {
            try {
                sorting = DirectionOfSorting.valueOf(sortDirection.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.info("Wrong sort direction passed. Rendered to default which is ascending");
                sorting = DirectionOfSorting.ASC;
            }
        }
        return sorting;
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

    private <T> Page<T> createPage(int pageNo, int pageSize, List<T> list, String sorting, String sortProperty) {
        // Handle empty list
        if (list.isEmpty()) {
            return Page.empty();
        }

        DirectionOfSorting sort = getDirectionOfSorting(sorting);

        Sort.Direction sortDirection = sort.equals(DirectionOfSorting.ASC) ? Sort.Direction.ASC : Sort.Direction.DESC;

        //if sort by property is not specified, then leave it.
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortProperty));

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

}
