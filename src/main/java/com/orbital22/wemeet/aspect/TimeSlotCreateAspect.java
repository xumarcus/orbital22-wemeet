package com.orbital22.wemeet.aspect;

import com.orbital22.wemeet.dto.TimeSlotDto;
import com.orbital22.wemeet.mapper.TimeSlotMapper;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.security.ValidationHelper;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class TimeSlotCreateAspect {
  private final ValidationHelper<TimeSlotDto> validator;

  @Pointcut("execution(* com.orbital22.wemeet.repository.TimeSlotRepository.save(*))")
  public void save() {}

  // No ACL to register
  @Before("com.orbital22.wemeet.aspect.TimeSlotCreateAspect.save() && args(timeSlot)")
  private void handleSave(TimeSlot timeSlot) {
    validator.validate(TimeSlotMapper.INSTANCE.timeSlotToTimeSlotDto(timeSlot));
  }
}
