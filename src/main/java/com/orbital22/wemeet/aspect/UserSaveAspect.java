package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static org.springframework.security.acls.domain.BasePermission.*;

@Aspect
@Component
@AllArgsConstructor
public class UserSaveAspect {
  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.UserRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.UserSaveAspect.save() && args(user)")
  private void handleBeforeSave(User user) {
    userService.handleBeforeSave(user);
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.UserSaveAspect.save()",
      returning = "user")
  private void handleAfterSave(User user) {
    if (user.isRegistered()) {
      userService.setSessionUser(user);
    }
    // READ permission is unused
    aclRegisterService.register(user, user.getEmail(), READ, WRITE, DELETE);
  }
}
