package com.betheagent.betheagent.controller.service;

import com.betheagent.betheagent.dto.requestDto.LoginDto;
import com.betheagent.betheagent.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.dto.responseDto.AuthenticationResponseDto;
import com.betheagent.betheagent.entity.UserInstance;
import com.betheagent.betheagent.enums.AuthenticationType;
import com.betheagent.betheagent.exception.customExceptions.UsernameOrEmailAlreadyExistsException;
import com.betheagent.betheagent.repository.TokenRepository;
import com.betheagent.betheagent.repository.UserRepository;
import com.betheagent.betheagent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    public AuthenticationResponseDto login(LoginDto loginDto) {
        return null;
    }

    @Override
    public AuthenticationResponseDto signup(SignUpDto signUpDto) {

        Boolean isConflict = userRepository.existsByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail());

        if (isConflict) {
            throw new UsernameOrEmailAlreadyExistsException("Username or email already exists");
        }

       UserInstance user = UserInstance.builder()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .firstname(signUpDto.getFirstname())
                .lastname(signUpDto.getLastname())
                .email(signUpDto.getEmail())
                .build();

        userRepository.save(user);
        return AuthenticationResponseDto.builder()
                .authenticationType(AuthenticationType.SIGN_UP)
                .username(user.getUsername())
                .firstName(user.getFirstname())
                .middleName(user.getMiddlename())
                .email(user.getEmail())
                .lastName(user.getLastname())
                .build();
    }
}
