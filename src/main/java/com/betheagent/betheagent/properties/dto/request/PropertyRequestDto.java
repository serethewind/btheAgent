package com.betheagent.betheagent.properties.dto.request;

import com.betheagent.betheagent.properties.dto.enums.PropertyType;
import com.betheagent.betheagent.properties.dto.enums.Rate;
import com.betheagent.betheagent.properties.dto.enums.Status;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PropertyRequestDto {
    private String name;
    private String description;
    private PropertyType propertyType; //enum of properties
    private int numberOfBedrooms;
    private Rate rate; //enums i.e. monthly, quarterly, yearly
    private BigDecimal price;
    private int numberOfBathrooms;
    private int maximumOccupancy;
    private List<String> amenities; //when it comes in, i will split it by comma and create a collection.
    private List<String> images;
    private List<String> videos;
    private Status availabilityStatus; //enum of availabilityStatus

    /*
Property ID
Title/Name of the property
Description
Location (including city, neighborhood, and coordinates)
Property type (Apartment, House, Condo, etc.)
Number of bedrooms
Number of bathrooms
Maximum occupancy
Amenities (e.g., Wi-Fi, AC, parking)
Price per night/rent
Availability status
Images (List of Image URLs or file references)
Videos (List of Video URLs or file references)
Additional media information (e.g., captions, order)
     */
}
