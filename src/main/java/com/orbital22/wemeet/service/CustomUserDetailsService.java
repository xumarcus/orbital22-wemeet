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
                .map(CustomUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
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
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return user.isEnabled();
        }

        @Override
        public boolean isAccountNonLocked() {
            return user.isEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return user.isEnabled();
        }

        @Override
        public boolean isEnabled() {
            return user.isEnabled();
        }
    }
}
