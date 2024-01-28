package com.betheagent.betheagent.properties.entity;

import com.betheagent.betheagent.audit.BaseEntity;
import com.betheagent.betheagent.properties.dto.enums.PropertyType;
import com.betheagent.betheagent.properties.dto.enums.Rate;
import com.betheagent.betheagent.properties.dto.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PropertyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String authorId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    private int numberOfBedrooms;
    @Enumerated(EnumType.STRING)
    private Rate rate;
    private BigDecimal price;
    private int numberOfBathrooms;
    private int maximumOccupancy;
    private List<String> amenities;
    private List<String> images;
    private List<String> videos;
    @Enumerated(EnumType.STRING)
    private Status availabilityStatus;

}
