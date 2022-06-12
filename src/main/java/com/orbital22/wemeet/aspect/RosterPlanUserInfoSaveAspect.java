package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.security.AclRegisterService;
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
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanUserInfoRepository.save(*))")
  public void save() {}

  // No fields to validate
  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanUserInfoSaveAspect.save()",
      returning = "rosterPlanUserInfo")
  private void handleAfterSave(RosterPlanUserInfo rosterPlanUserInfo) {
    String email = rosterPlanUserInfo.getUser().getEmail();
    aclRegisterService.register(rosterPlanUserInfo, email, READ, WRITE, DELETE);
    aclRegisterService.register(rosterPlanUserInfo.getRosterPlan(), email, READ);
  }
}
