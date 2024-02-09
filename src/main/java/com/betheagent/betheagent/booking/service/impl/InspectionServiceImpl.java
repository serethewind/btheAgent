package com.betheagent.betheagent.booking.service.impl;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import com.betheagent.betheagent.booking.dto.enums.InspectionStatus;
import com.betheagent.betheagent.booking.dto.request.CreateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.request.UpdateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.response.InspectionResponseDto;
import com.betheagent.betheagent.booking.entity.InspectionBooking;
import com.betheagent.betheagent.booking.repository.InspectionRepository;
import com.betheagent.betheagent.booking.service.InspectionService;
import com.betheagent.betheagent.exception.customExceptions.BadRequestException;
import com.betheagent.betheagent.exception.customExceptions.BookingResourceNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.UserResourceNotFoundException;
import com.betheagent.betheagent.properties.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InspectionServiceImpl implements InspectionService {

    private final InspectionRepository inspectionRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public InspectionResponseDto createBookingForInspection(String propertyId, CreateInspectionRequestDto inspectionRequestDto) {

        UserInstance user = userRepository.findById(inspectionRequestDto.getUserId()).orElseThrow(() -> new UserResourceNotFoundException("User not found. Create booking operation failed"));
        propertyRepository.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException("Property not found. Create booking operation failed"));

        InspectionBooking booking = InspectionBooking.builder()
                .userId(inspectionRequestDto.getUserId())
                .propertyId(user.getId())
                .inspectionDateTime(inspectionRequestDto.getInspectionDateTime())
                .comment(inspectionRequestDto.getComment())
                .inspectionStatus(InspectionStatus.PENDING)
                .build();

        inspectionRepository.save(booking);
        return mapBookingEntityToDto(booking);
    }

    @Override
    public InspectionResponseDto updateBookingDetails(String inspectionId, UpdateInspectionRequestDto inspectionRequestDto) {
        return null;
    }

    @Override
    public InspectionResponseDto findBookingById(String inspectionId) {
        InspectionBooking booking = inspectionRepository.findById(inspectionId).orElseThrow(() -> new BookingResourceNotFoundException("Booking not found. Find booking by id operation failed"));
        return mapBookingEntityToDto(booking);
    }

    @Override
    public Page<InspectionResponseDto> findAllBookings(Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
        List<InspectionResponseDto> inspectionResponseDtoList = inspectionRepository.findAll().stream().map(this::mapBookingEntityToDto).collect(Collectors.toList());
        return createPage(pageNo, pageSize, inspectionResponseDtoList);

    }

    @Override
    public Page<InspectionResponseDto> findAllBookingsByUserId(String userId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
       List<InspectionResponseDto> inspectionResponseDtoList = inspectionRepository.findAllBookingsByUserId(userId).map(this::mapBookingEntityToDto).toList();
       return createPage(pageNo, pageSize, inspectionResponseDtoList);
    }

    @Override
    public Page<InspectionResponseDto> findAllBookingsByPropertyId(String propertyId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn) {
      List<InspectionResponseDto> inspectionResponseDtoList = inspectionRepository.findAllBookingsByPropertyId(propertyId).map(this::mapBookingEntityToDto).toList();
      return createPage(pageNo, pageSize, inspectionResponseDtoList);
    }

    @Override
    public InspectionResponseDto cancelBooking(String propertyId) {
       InspectionBooking booking = inspectionRepository.findById(propertyId).orElseThrow(() -> new BookingResourceNotFoundException("Booking resource not found. Cancel Booking operation failed."));
       if (booking.getInspectionStatus().equals(InspectionStatus.CANCELLED)) {
           throw new BadRequestException("Booking already cancelled. Cancel Booking operation failed");
       }
       booking.setInspectionStatus(InspectionStatus.CANCELLED);
       inspectionRepository.save(booking);
       return mapBookingEntityToDto(booking);
    }
    @Override
    public InspectionResponseDto confirmBookingStatus(String propertyId) {
        InspectionBooking booking = inspectionRepository.findById(propertyId).orElseThrow(() -> new BookingResourceNotFoundException("Booking resource not found. Cancel Booking operation failed."));
        if (booking.getInspectionStatus().equals(InspectionStatus.CONFIRMED)) {
            throw new BadRequestException("Booking already confirmed. Cancel Booking operation failed");
        }
        booking.setInspectionStatus(InspectionStatus.CONFIRMED);
        inspectionRepository.save(booking);
        return mapBookingEntityToDto(booking);
    }

    private InspectionResponseDto mapBookingEntityToDto(InspectionBooking booking) {
        return InspectionResponseDto.builder()
                .status(booking.getInspectionStatus())
                .inspectionId(booking.getId())
                .userId(booking.getUserId())
                .propertyId(booking.getPropertyId())
                .inspectionDateTime(booking.getInspectionDateTime())
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
}
