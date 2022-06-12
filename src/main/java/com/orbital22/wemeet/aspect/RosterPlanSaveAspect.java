package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.RosterPlanService;
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
public class RosterPlanSaveAspect {
  private final UserRepository userRepository;
  private final AclRegisterService aclRegisterService;
  private final RosterPlanService rosterPlanService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.RosterPlanSaveAspect.save() && args(rosterPlan)")
  private void handleBeforeSave(RosterPlan rosterPlan) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User owner = userRepository.findByEmail(authentication.getName()).orElseThrow();
    rosterPlan.setOwner(owner);

    if (rosterPlan.getId() != 0) {
      /*
      MODIFIED -> QUEUED: Fire solver job
      MODIFIED -> SOLVED: Forbidden
      QUEUED -> SOLVED: Update status and snapshot
      QUEUED -> MODIFIED: Ok
      SOLVED -> MODIFIED: Ok
      SOLVED -> QUEUED: Forbidden
       */
      rosterPlanService.handleStatusChange(rosterPlan);
    }
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanSaveAspect.save()",
      returning = "rosterPlan")
  private void handleAfterSave(RosterPlan rosterPlan) {
    aclRegisterService.register(rosterPlan, rosterPlan.getOwner().getEmail(), READ, WRITE, DELETE);
  }
}
