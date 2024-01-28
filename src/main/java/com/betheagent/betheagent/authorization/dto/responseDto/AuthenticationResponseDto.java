package com.betheagent.betheagent.authorization.dto.responseDto;

import com.betheagent.betheagent.authorization.enums.AuthenticationType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationResponseDto {
    private AuthenticationType authenticationType;
    private String message;

    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String gender;

    private String token;
    private String refreshToken;
}
