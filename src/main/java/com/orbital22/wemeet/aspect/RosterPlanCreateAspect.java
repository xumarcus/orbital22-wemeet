package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.security.acls.domain.BasePermission.*;

@Aspect
@Component
@AllArgsConstructor
public class RosterPlanCreateAspect {
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.RosterPlanCreateAspect.save() && args(rosterPlan)")
  private void handleBeforeSave(RosterPlan rosterPlan) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User owner = userRepository.findByEmail(authentication.getName()).orElseThrow();
    rosterPlan.setOwner(owner);
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanCreateAspect.save()",
      returning = "rosterPlan")
  private void handleAfterSave(RosterPlan rosterPlan) {
    aclRegisterService.register(rosterPlan, rosterPlan.getOwner().getEmail(), READ, WRITE, DELETE);
  }
}
