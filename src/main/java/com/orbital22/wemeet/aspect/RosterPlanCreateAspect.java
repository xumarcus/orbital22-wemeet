package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.mapper.RosterPlanMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.security.AclRegisterService;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static org.springframework.security.acls.domain.BasePermission.*;

@Aspect
@Component
@AllArgsConstructor
public class RosterPlanCreateAspect {
  private final RosterPlanService rosterPlanService;
  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.RosterPlanRepository.save(*))")
  public void save() {}

  @Around("com.orbital22.wemeet.aspect.RosterPlanCreateAspect.save() && args(rosterPlan)")
  private RosterPlan handleSave(ProceedingJoinPoint pjp, RosterPlan rosterPlan) throws Throwable {
    rosterPlanService.validate(RosterPlanMapper.INSTANCE.rosterPlanToRosterPlanDto(rosterPlan));

    User owner = userService.me().orElseThrow();
    rosterPlan.setOwner(owner);
    RosterPlan saved = (RosterPlan) pjp.proceed();
    aclRegisterService.register(saved, owner.getEmail(), READ, WRITE, DELETE);
    return saved;
  }
}
