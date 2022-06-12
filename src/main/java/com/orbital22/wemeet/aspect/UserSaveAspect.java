package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static org.springframework.security.acls.domain.BasePermission.*;

@Aspect
@Component
@AllArgsConstructor
public class UserSaveAspect {
  private final PasswordEncoder passwordEncoder;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.UserRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.UserSaveAspect.save() && args(user)")
  private void handleBeforeSave(User user) {
    if (user.isRegistered()) {
      user.setPassword(passwordEncoder.encode(user.getRawPassword()));
    }
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.UserSaveAspect.save()",
      returning = "user")
  private void handleAfterSave(User user) {
    if (user.isRegistered()) {
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  user.getEmail(), null, Collections.emptySet()));
    }
    aclRegisterService.register(user, user.getEmail(), READ, WRITE, DELETE);
  }
}
