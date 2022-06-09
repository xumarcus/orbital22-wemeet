package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.mapper.UserMapper;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
public class UserCreateAspect {
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.UserRepository.save(*))")
  public void save() {}

  @Around("com.orbital22.wemeet.aspect.UserCreateAspect.save() && args(user)")
  private User handleSave(ProceedingJoinPoint pjp, User user) throws Throwable {
    userService.validate(UserMapper.INSTANCE.userToUserDto(user));

    if (user.isRegistered()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    User saved = (User) pjp.proceed();
    if (saved.isRegistered()) {
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  saved.getEmail(), null, Collections.emptySet()));
    }
    aclRegisterService.register(saved, saved.getEmail(), READ, WRITE, DELETE);
    return saved;
  }
}
