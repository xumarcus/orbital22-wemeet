package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .map(CustomUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @AllArgsConstructor
    static class CustomUserPrincipal implements UserDetails {
        private User user;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getAuthorities();
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return user.getEnabled();
        }

        @Override
        public boolean isAccountNonLocked() {
            return user.getEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return user.getEnabled();
        }

        @Override
        public boolean isEnabled() {
            return user.getEnabled();
        }
    }
}
