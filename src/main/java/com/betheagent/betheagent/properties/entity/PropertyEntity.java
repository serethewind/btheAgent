//package com.betheagent.betheagent.properties.entity;
//
//import com.betheagent.betheagent.audit.BaseEntity;
//import com.betheagent.betheagent.properties.dto.enums.PropertyType;
//import com.betheagent.betheagent.properties.dto.enums.Rate;
//import com.betheagent.betheagent.properties.dto.enums.Status;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//public class PropertyEntity extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
//    private String authorId;
//    private String name;
//    private String description;
//    @Enumerated(EnumType.STRING)
//    private PropertyType propertyType;
//    private int numberOfBedrooms;
//    @Enumerated(EnumType.STRING)
//    private Rate rate;
//    private BigDecimal price;
//    private int numberOfBathrooms;
//    private int maximumOccupancy;
//
//    @ElementCollection
//    @CollectionTable(
//            name = "property_amenities",
//            joinColumns = @JoinColumn(name = "id")
//    )
//    @Column(name = "property_amenity")
//    private List<String> amenities;
//
//    @ElementCollection
//    @CollectionTable(
//            name = "property_images",
//            joinColumns = @JoinColumn(name = "id")
//    )
//    @Column(name = "property-image")
//    private List<String> images;
//
//    @ElementCollection
//    @CollectionTable(
//            name = "property_videos",
//            joinColumns = @JoinColumn(name = "id")
//    )
//    @Column(name = "property_video")
//    private List<String> videos;
//    @Enumerated(EnumType.STRING)
//    private Status availabilityStatus;
//
//}
