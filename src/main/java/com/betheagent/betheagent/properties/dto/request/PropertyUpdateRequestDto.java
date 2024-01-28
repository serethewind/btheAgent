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
public class PropertyUpdateRequestDto {

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
}
