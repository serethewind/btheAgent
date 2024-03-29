package com.betheagent.betheagent.dto.requestDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {

    private String userNameOrEmail;
    private String password;
}
