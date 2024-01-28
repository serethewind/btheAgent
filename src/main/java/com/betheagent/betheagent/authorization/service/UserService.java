package com.betheagent.betheagent.authorization.service;

import com.betheagent.betheagent.authorization.dto.requestDto.LoginDto;
import com.betheagent.betheagent.authorization.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.authorization.dto.responseDto.AuthenticationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UserService {
    AuthenticationResponseDto login(LoginDto loginDto);

    AuthenticationResponseDto signup(SignUpDto signUpDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
