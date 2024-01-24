package com.betheagent.betheagent.controller;

import com.betheagent.betheagent.dto.requestDto.LoginDto;
import com.betheagent.betheagent.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.dto.responseDto.AuthenticationResponseDto;
import com.betheagent.betheagent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reg")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @RequestMapping("login")
    public ResponseEntity<AuthenticationResponseDto> userLogin(@RequestBody LoginDto loginDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginDto));
    }

    @RequestMapping("signup")
    public ResponseEntity<AuthenticationResponseDto> userSignUp(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(signUpDto));
    }
}
