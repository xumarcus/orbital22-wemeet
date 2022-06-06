package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.acls.domain.BasePermission.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;

  public User register(String email, String password) {
    if (userRepository.existsByEmail(email)) {
      throw new RuntimeException();
    }
    User user = userRepository.save(User.ofRegistered(email, passwordEncoder.encode(password)));
    aclRegisterService.register(user, email, READ, WRITE, DELETE);
    log.info("Email " + email + " registered.");
    return user;
  }

  public Set<User> makeFromEmails(Collection<String> emails) {
    Set<User> users = userRepository.findByEmailIn(emails);
    Set<String> remainingEmails = users.stream().map(User::getEmail).collect(Collectors.toSet());
    users.addAll(
        emails.stream()
            .filter(remainingEmails::contains)
            .map(User::ofUnregistered)
            .collect(Collectors.toSet()));
    return users;
  }
}
