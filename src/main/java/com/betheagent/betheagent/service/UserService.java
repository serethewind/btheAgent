package com.betheagent.betheagent.service;

import com.betheagent.betheagent.dto.requestDto.LoginDto;
import com.betheagent.betheagent.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.dto.responseDto.AuthenticationResponseDto;

public interface UserService {
    AuthenticationResponseDto login(LoginDto loginDto);

    AuthenticationResponseDto signup(SignUpDto signUpDto);
}
