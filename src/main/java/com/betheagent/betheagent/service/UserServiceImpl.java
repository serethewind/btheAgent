package com.betheagent.betheagent.service;

import com.betheagent.betheagent.config.CustomUserDetailsService;
import com.betheagent.betheagent.config.JWTService;
import com.betheagent.betheagent.dto.requestDto.LoginDto;
import com.betheagent.betheagent.dto.requestDto.SignUpDto;
import com.betheagent.betheagent.dto.responseDto.AuthenticationResponseDto;
import com.betheagent.betheagent.entity.TokenEntity;
import com.betheagent.betheagent.entity.UserInstance;
import com.betheagent.betheagent.entity.enums.RoleType;
import com.betheagent.betheagent.entity.enums.TokenType;
import com.betheagent.betheagent.enums.AuthenticationType;
import com.betheagent.betheagent.exception.customExceptions.UsernameOrEmailAlreadyExistsException;
import com.betheagent.betheagent.repository.TokenRepository;
import com.betheagent.betheagent.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserInstance user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        String token = jwtService.generateTokenWithClaims(user.getUsername(), generateClaimsFromUser(user.getUsername()));
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), generateClaimsFromUser(user.getUsername()));
        revokeValidTokens(user);
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .user(user)
                .build();
        tokenRepository.save(tokenEntity);

        AuthenticationResponseDto authenticationResponseDto = mapToAuthenticationResponse(AuthenticationType.LOGIN, user, "LOGIN SUCCESSFUL");
        authenticationResponseDto.setToken(token);
        authenticationResponseDto.setRefreshToken(refreshToken);
        return authenticationResponseDto;
    }

    @Override
    public AuthenticationResponseDto signup(SignUpDto signUpDto) {

        boolean isConflict = userRepository.existsByUsernameOrEmail(signUpDto.getUsername(), signUpDto.getEmail());

        if (isConflict) {
            throw new UsernameOrEmailAlreadyExistsException("Username or email already exists");
        }

        UserInstance user = UserInstance.builder()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .firstname(signUpDto.getFirstname())
                .lastname(signUpDto.getLastname())
                .email(signUpDto.getEmail())
                .roleTypes(Set.of(RoleType.TENANT))
                .isEnabled(false)
                .build();

        userRepository.save(user);
        return mapToAuthenticationResponse(AuthenticationType.SIGN_UP, user, "SIGNUP SUCCESSFUL");
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final java.lang.String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        java.lang.String refreshToken = authHeader.substring(7);
        java.lang.String username = jwtService.getUsername(refreshToken);

        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            var user = this.userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Show yourself if you are the problem"));

            if (jwtService.isTokenValid(refreshToken, userDetails)) {

                Map<java.lang.String, Object> claims = generateClaimsFromUser(user.getUsername());

                var accessToken = jwtService.generateTokenWithClaims(user.getUsername(), claims);
                revokeValidTokens(user);
                TokenEntity tokenEntity = TokenEntity.builder()
                        .user(user)
                        .token(accessToken)
                        .tokenType(TokenType.BEARER)
                        .isExpired(false)
                        .isRevoked(false)
                        .build();

                var t = tokenRepository.save(tokenEntity);
                var authResponse = AuthenticationResponseDto.builder()
                        .message("Refresh token used to generate access token successfully")
                        .userId(user.getId())
                        .authenticationType(AuthenticationType.REFRESH_TOKEN)
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void revokeValidTokens(UserInstance user) {
        List<TokenEntity> tokenList = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (tokenList.isEmpty())
            return;
        tokenList.forEach(t -> {
            t.setIsRevoked(true);
            t.setIsExpired(true);
        });
        tokenRepository.deleteAll(tokenList);
    }

    private AuthenticationResponseDto mapToAuthenticationResponse(AuthenticationType authenticationType, UserInstance user, String message){
        return AuthenticationResponseDto.builder()
                .authenticationType(authenticationType)
                .message(message)
                .userId(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstname())
                .middleName(user.getMiddlename())
                .email(user.getEmail())
                .lastName(user.getLastname())
                .build();
    }

    private Map<String, Object> generateClaimsFromUser(String username){
        UserInstance user =  userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoleTypes());
        claims.put("email", user.getEmail());
        return claims;
    }

}
