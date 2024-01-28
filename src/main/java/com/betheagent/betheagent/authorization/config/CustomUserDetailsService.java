package com.betheagent.betheagent.authorization.config;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInstance user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(() -> new UsernameNotFoundException("User with username or email not found"));

        return new CustomUserDetails(user);
    }
}
