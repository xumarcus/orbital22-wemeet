package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
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
public class RosterPlanUserInfoSaveAspect {
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanUserInfoRepository.save(*))")
  public void save() {}

  @Before(
      "com.orbital22.wemeet.aspect.RosterPlanUserInfoSaveAspect.save() && args(rosterPlanUserInfo)")
  private void handleBeforeSave(RosterPlanUserInfo rosterPlanUserInfo) {
    String email = rosterPlanUserInfo.getTransientEmail();
    if (email != null) {
      rosterPlanUserInfo.setUser(
          userRepository
              .findByEmail(email)
              .orElseGet(() -> userRepository.save(User.ofUnregistered(email))));
    }
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanUserInfoSaveAspect.save()",
      returning = "rosterPlanUserInfo")
  private void handleAfterSave(RosterPlanUserInfo rosterPlanUserInfo) {
    String email = rosterPlanUserInfo.getUser().getEmail();
    aclRegisterService.register(rosterPlanUserInfo, email, READ, WRITE, DELETE);
    aclRegisterService.register(rosterPlanUserInfo.getRosterPlan(), email, READ);
  }
}
