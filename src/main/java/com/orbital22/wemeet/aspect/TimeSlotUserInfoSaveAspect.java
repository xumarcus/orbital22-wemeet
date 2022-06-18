package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.model.TimeSlotUserInfo;
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
public class TimeSlotUserInfoSaveAspect {
  private final UserService userService;
  private final AclRegisterService aclRegisterService;

  @Pointcut("execution(* com.orbital22.wemeet.repository.TimeSlotUserInfoRepository.save(*))")
  public void save() {}

  @Before("com.orbital22.wemeet.aspect.TimeSlotUserInfoSaveAspect.save() && args(timeSlotUserInfo)")
  private void handleBeforeSave(TimeSlotUserInfo timeSlotUserInfo) {
    timeSlotUserInfo.setUser(userService.me().orElseThrow());
  }

  @AfterReturning(
      pointcut = "com.orbital22.wemeet.aspect.TimeSlotUserInfoSaveAspect.save()",
      returning = "timeSlotUserInfo")
  private void handleAfterSave(TimeSlotUserInfo timeSlotUserInfo) {
    aclRegisterService.register(
        timeSlotUserInfo, timeSlotUserInfo.getUser().getEmail(), READ, WRITE, DELETE);
  }
}
