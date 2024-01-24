package com.betheagent.betheagent.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileUpdateDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
}
