package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import static org.springframework.security.acls.domain.BasePermission.*;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;
  private AclRegisterService aclRegisterService;

  public void register(String email, String password) {
    Assert.isTrue(!userRepository.existsByEmail(email), "Email exists");
    User user = User.ofRegistered(email, passwordEncoder.encode(password));
    userRepository.save(user);
    aclRegisterService.register(user, email, READ, WRITE, DELETE);
  }
}
