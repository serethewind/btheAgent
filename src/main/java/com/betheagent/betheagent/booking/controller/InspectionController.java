package com.betheagent.betheagent.booking.controller;

import com.betheagent.betheagent.booking.dto.enums.InspectionStatus;
import com.betheagent.betheagent.booking.dto.request.CreateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.request.UpdateInspectionRequestDto;
import com.betheagent.betheagent.booking.dto.response.InspectionResponseDto;
import com.betheagent.betheagent.booking.service.InspectionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("v1/api/btheagent/properties/inspection")
@RequiredArgsConstructor
@RestController
public class InspectionController {

    private final InspectionService inspectionService;

    @PostMapping("/book-for-inspection/{propertyId}")
    public ResponseEntity<InspectionResponseDto> createBooking(@PathVariable("propertyId") String propertyId, @RequestBody CreateInspectionRequestDto inspectionRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(inspectionService.createBookingForInspection(propertyId, inspectionRequestDto));
    }

    @PutMapping("/update-booking-details/{inspectionId}")
    public ResponseEntity<InspectionResponseDto> updateBooking(@PathVariable("inspectionId")String inspectionId, @RequestBody UpdateInspectionRequestDto inspectionRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(inspectionService.updateBookingDetails(inspectionId, inspectionRequestDto));
    }

    @GetMapping("/view-booking-details/")
    public ResponseEntity<InspectionResponseDto> fetchBookingDetailsById(@RequestParam("inspectionId") String inspectionId){
        return ResponseEntity.status(HttpStatus.OK).body(inspectionService.findBookingById(inspectionId));
    }

    /**
     @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
     @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
     @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn
     */


    @GetMapping("view-all-bookings")
    public ResponseEntity<Page<InspectionResponseDto>> fetchAllBookings(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "byColumn", required = false, defaultValue = "id") String byColumn
    ){
        return ResponseEntity.status(HttpStatus.OK).body(inspectionService.findAllBookings(pageNo, pageSize, sortDirection, byColumn));
    }

    @GetMapping("{userId}/view-all-bookings")
    public ResponseEntity<Page<InspectionResponseDto>> fetchAllBookingsByUserId(
            @PathVariable("userId") String userId,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn
     ){
        return ResponseEntity.ok(inspectionService.findAllBookingsByUserId(userId, pageNo, pageSize, sortDirection, byColumn));
    }

    @GetMapping("{propertyId}/view-all-bookings")
    public ResponseEntity<Page<InspectionResponseDto>> fetchAllBookingsByPropertyId(
            @PathVariable("propertyId") String propertyId,
            @RequestParam(name = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "byColumn", required = false, defaultValue = "id") String byColumn
    ){
        return ResponseEntity.status(HttpStatus.OK).body(inspectionService.findAllBookingsByPropertyId(propertyId, pageNo, pageSize, sortDirection, byColumn));
    }
}
