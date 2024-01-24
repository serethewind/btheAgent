package com.betheagent.betheagent.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignUpDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
}
