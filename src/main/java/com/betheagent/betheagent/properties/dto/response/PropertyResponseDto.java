package com.betheagent.betheagent.properties.dto.response;

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
public class PropertyResponseDto {
    private String id;
    private String authorId;
    private String name;
    private String description;
    private PropertyType propertyType;
    private int numberOfBedrooms;
    private Rate rate;
    private BigDecimal price;
    private int numberOfBathrooms;
    private int maximumOccupancy;
    private List<String> amenities; //when it comes in, i will split it by comma and create a collection.
    private List<String> images;
    private List<String> videos;
    private Status availabilityStatus; //enum of availabilityStatus
}
