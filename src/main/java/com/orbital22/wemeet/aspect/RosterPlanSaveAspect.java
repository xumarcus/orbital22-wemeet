package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.RosterPlanService;
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
public class RosterPlanSaveAspect {
  private final UserService userService;
  private final AclRegisterService aclRegisterService;
  private final RosterPlanService rosterPlanService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.RosterPlanSaveAspect.save() && args(rosterPlan)")
  private void handleBeforeSave(RosterPlan rosterPlan) {
    rosterPlan.setOwner(userService.me().orElseThrow());
    if (rosterPlanService.canSolve(rosterPlan)) {
      rosterPlanService.solve(rosterPlan);
    }
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.RosterPlanSaveAspect.save()",
      returning = "rosterPlan")
  private void handleAfterSave(RosterPlan rosterPlan) {
    aclRegisterService.register(rosterPlan, rosterPlan.getOwner().getEmail(), READ, WRITE, DELETE);
  }
}
