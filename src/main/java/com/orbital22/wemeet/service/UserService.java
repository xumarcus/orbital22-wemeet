package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.security.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;
  private final AuthenticationManager authenticationManager;

  public Optional<User> register(AuthRegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      return Optional.empty(); // Validated before
    }

    User user =
        userRepository.save(
            User.ofRegistered(request.getEmail(), passwordEncoder.encode(request.getPassword())));

    UsernamePasswordAuthenticationToken authReq =
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    SecurityContextHolder.getContext()
        .setAuthentication(authenticationManager.authenticate(authReq));

    aclRegisterService.register(user, user.getEmail(), READ, WRITE, DELETE);
    return Optional.of(user);
  }

  Set<User> fromEmails(Collection<String> emails) {
    Set<User> users = userRepository.findByEmailIn(emails);
    users.addAll(
        userRepository
            .saveAll(
                () ->
                    emails.stream()
                        .filter(
                            email -> users.stream().map(User::getEmail).noneMatch(email::equals))
                        .map(User::ofUnregistered)
                        .iterator())
            .stream()
            .peek(user -> aclRegisterService.register(user, user.getEmail(), READ, WRITE, DELETE))
            .collect(Collectors.toSet()));
    return users;
  }

  public Optional<User> fromPrincipal(Principal principal) {
    return userRepository.findById(((CustomUser) principal).getId());
  }
}
