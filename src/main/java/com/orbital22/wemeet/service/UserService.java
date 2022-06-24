package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

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

  public void handleBeforeSave(User user) {
    if (user.getId() != 0) {
      if (!user.isRegistered()) {
        throw ProjectUtils.from(user, User.Fields.registered, "CANNOT_UNREGISTER_USER");
      }
    } else {
      Optional<User> opt = userRepository.findByEmail(user.getEmail());
      if (user.isRegistered()) {
        opt.ifPresent(
            currentUser -> {
              if (currentUser.isRegistered()) {
                throw ProjectUtils.from(user, User.Fields.email, "USER_IS_ALREADY_REGISTERED");
              }
              user.setId(currentUser.getId());
            });
      } else {
        if (opt.isPresent()) {
          throw ProjectUtils.from(user, User.Fields.email, "CANNOT_CREATE_USER_WITH_SAME_EMAIL");
        }
      }
    }

    if (user.isRegistered()) {
      user.setPassword(passwordEncoder.encode(user.getRawPassword()));
    }
  }
}
