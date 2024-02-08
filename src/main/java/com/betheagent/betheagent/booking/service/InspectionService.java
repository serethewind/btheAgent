package com.betheagent.betheagent.booking.service;

import com.betheagent.betheagent.booking.dto.request.CreateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.request.UpdateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.response.InspectionResponseDto;
import org.springframework.data.domain.Page;

public interface InspectionService {
    InspectionResponseDto createBookingForInspection(String propertyId, CreateInspectionRequestDto inspectionRequestDto);
    InspectionResponseDto updateBookingDetails(String inspectionId, UpdateInspectionRequestDto inspectionRequestDto);
    InspectionResponseDto findBookingById(String inspectionId);

    Page<InspectionResponseDto> findAllBookings(Integer pageNo, Integer pageSize, String sortDirection, String byColumn);
    Page<InspectionResponseDto> findAllBookingsByUserId(String userId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn);

    Page<InspectionResponseDto> findAllBookingsByPropertyId(String propertyId, Integer pageNo, Integer pageSize, String sortDirection, String byColumn);
}
