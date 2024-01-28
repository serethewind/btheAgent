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

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@EnableMethodSecurity
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("{userId}/create")
    public ResponseEntity<PropertyResponseDto> createProperty(@PathVariable("userId") String userId, @RequestBody PropertyRequestDto propertyRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.createProperty(userId, propertyRequestDto));
    }

    @GetMapping("viewProperty/{propertyId}")
    public ResponseEntity<PropertyResponseDto> viewSingleProperty(@PathVariable("propertyId") String propertyId){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyById(propertyId));
    }

    @GetMapping("all-properties")
    public ResponseEntity<List<PropertyResponseDto>> viewAllProperties(){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.viewAllProperties());
    }

    @GetMapping("{userId}/properties")
    public ResponseEntity<List<PropertyResponseDto>> viewAllPropertiesByUser(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.viewAllPropertiesByUser(userId));
    }


    @PutMapping("{propertyId}/update")
    public ResponseEntity<PropertyResponseDto> updateSingleProperty(@PathVariable("propertyId")String propertyId, @RequestBody PropertyUpdateRequestDto propertyUpdateRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updatePropertyById(propertyId, propertyUpdateRequestDto));
    }

    @DeleteMapping("{propertyId}/remove")
    public ResponseEntity<PropertyResponseDto> softDeleteSinglePropertyInstance(@PathVariable("propertyId")String propertyId){
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.removePropertyById(propertyId));
    }

//    Sorting operations for properties by status, by date etc...
}
