package com.betheagent.betheagent.booking.dto.response;

import com.betheagent.betheagent.booking.dto.enums.InspectionStatus;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InspectionResponseDto {
    private String inspectionId;
    private String userId;
    private String propertyId;
    private LocalDateTime inspectionDateTime;
    private InspectionStatus status;
}
