package com.betheagent.betheagent.properties.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishListRequestDto {
    private String propertyId;
    private String comment;
}
