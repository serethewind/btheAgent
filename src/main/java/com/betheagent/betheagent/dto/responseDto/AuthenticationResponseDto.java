package com.betheagent.betheagent.dto.responseDto;

import com.betheagent.betheagent.enums.AuthenticationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponseDto {
    private AuthenticationType authenticationType;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String gender;
}
