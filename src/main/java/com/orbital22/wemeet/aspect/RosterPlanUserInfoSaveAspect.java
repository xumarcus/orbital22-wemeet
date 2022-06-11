package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static org.springframework.security.acls.domain.BasePermission.*;

@Aspect
@Component
@AllArgsConstructor
public class RosterPlanUserInfoSaveAspect {
  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanUserInfoRepository.save(*))")
  public void save() {}

  // No fields to validate
  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanUserInfoSaveAspect.save()",
      returning = "ret")
  private void afterSaving(RosterPlanUserInfo ret) {
    String email = ret.getUser().getEmail();
    aclRegisterService.register(ret, email, READ, WRITE, DELETE);
    aclRegisterService.register(ret.getRosterPlan(), email, READ);
  }
}
