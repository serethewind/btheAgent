package com.betheagent.betheagent.authorization.controller;

import com.betheagent.betheagent.authorization.dto.requestDto.LoginDto;
import com.betheagent.betheagent.authorization.config.LogoutService;
import com.betheagent.betheagent.authorization.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.authorization.dto.responseDto.AuthenticationResponseDto;
import com.betheagent.betheagent.authorization.enums.AuthenticationType;
import com.betheagent.betheagent.authorization.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/btheagent/reg")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;

    @RequestMapping("login")
    public ResponseEntity<AuthenticationResponseDto> userLogin(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginDto));
    }

    @RequestMapping("signup")
    public ResponseEntity<AuthenticationResponseDto> userSignUp(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(signUpDto));
    }

    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }

    @GetMapping("logout")
    public ResponseEntity<AuthenticationResponseDto> logout(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            Authentication authentication) {
        logoutService.logout(request, response, authentication);
        AuthenticationResponseDto responseDto = AuthenticationResponseDto.builder()
                .authenticationType(AuthenticationType.LOGOUT)
                .message("Logout successful")
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
