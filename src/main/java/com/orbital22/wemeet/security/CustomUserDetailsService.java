package com.orbital22.wemeet.security;

import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author xumarcus
 * <p>
 * This DAO furnishes UserDetails to Spring Security.
 * There is no need to inject this elsewhere.
 */

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
    return userRepository
        .findByEmail(email)
        .map(CustomUser::new)
        .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
