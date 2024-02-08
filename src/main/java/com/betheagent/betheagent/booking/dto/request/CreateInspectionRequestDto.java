package com.betheagent.betheagent.booking.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateInspectionRequestDto {
    private String userId;
    private String comment;
    private LocalDateTime inspectionDateTime;
}
