package com.betheagent.betheagent.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserInstance user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        String token = jwtService.generateTokenWithClaims(user.getUsername(), new HashMap<>());
        revokeValidTokens(user);
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .user(user)
                .build();
        tokenRepository.save(tokenEntity);

        return mapToAuthenticationResponse(AuthenticationType.LOGIN, user);
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
                .roleTypes(Set.of(RoleType.TENANT))
                .isEnabled(false)
                .build();

        userRepository.save(user);
        return mapToAuthenticationResponse(AuthenticationType.SIGN_UP, user);
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

    private AuthenticationResponseDto mapToAuthenticationResponse(AuthenticationType authenticationType, UserInstance user){
        return AuthenticationResponseDto.builder()
                .authenticationType(authenticationType)
                .username(user.getUsername())
                .firstName(user.getFirstname())
                .middleName(user.getMiddlename())
                .email(user.getEmail())
                .lastName(user.getLastname())
                .build();
    }
}
