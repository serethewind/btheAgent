package com.betheagent.betheagent.booking.entity;

import com.betheagent.betheagent.audit.BaseEntity;
import com.betheagent.betheagent.booking.dto.enums.InspectionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class InspectionBooking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String propertyId;
    private LocalDateTime inspectionDateTime;
    private InspectionStatus inspectionStatus;

    private String comment;
    private String feedback;
}
