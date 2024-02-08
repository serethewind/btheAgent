package com.betheagent.betheagent.booking.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateInspectionRequestDto {
    private String userId;
    private LocalDateTime newInspectionDateTime;
}
