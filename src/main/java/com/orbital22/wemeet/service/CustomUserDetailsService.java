package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.CustomUserPrincipal;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xumarcus
 *
 * This DAO furnishes UserDetails to Spring Security.
 * There is no need to inject this elsewhere.
 */

@Service
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @NonNull
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(CustomUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
