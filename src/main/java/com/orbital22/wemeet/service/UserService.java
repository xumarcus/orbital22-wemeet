package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.UserPostRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.util.ProjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;

  @NotNull
  public Optional<User> me() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByEmail(authentication.getName());
  }

  @NotNull
  public void setSessionUser(User user) {
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities()));
  }

  @NotNull
  public User post(UserPostRequest request) {
    int id =
        userRepository
            .findByEmail(request.getEmail())
            .map(
                existingUser -> {
                  if (existingUser.isRegistered()) {
                    throw ProjectUtils.from(
                        existingUser, User.Fields.email, "USER_IS_ALREADY_REGISTERED");
                  }
                  return existingUser.getId();
                })
            .orElse(0);

    User user =
        userRepository.save(
            User.builder()
                .id(id)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getRawPassword()))
                .enabled(true)
                .registered(true)
                .build());

    setSessionUser(user);

    // READ permission is unused
    aclRegisterService.register(user, user.getEmail(), READ, WRITE, DELETE);

    return user;
  }
}
