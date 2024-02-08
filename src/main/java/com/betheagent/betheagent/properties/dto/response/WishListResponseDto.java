package com.betheagent.betheagent.properties.dto.response;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishListResponseDto {
    private String id;
    private String status;
    private String comment;
    private String userId;
    private String propertyId;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    //what will be returned is the property image, property name, etc.
}
