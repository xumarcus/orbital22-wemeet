package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.RosterPlanStatusConstraint;
import com.orbital22.wemeet.enums.RosterPlanStatus;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class RosterPlanStatusValidator
    implements ConstraintValidator<RosterPlanStatusConstraint, RosterPlan> {
  private final RosterPlanRepository rosterPlanRepository;

  @Override
  public boolean isValid(RosterPlan rosterPlan, ConstraintValidatorContext ctx) {
    if (rosterPlan.getId() == 0) return true;
    RosterPlan prev = rosterPlanRepository.findById(rosterPlan.getId()).orElseThrow();
    if (prev.getRosterPlanStatus() == rosterPlan.getRosterPlanStatus()) return true;
    return prev.getRosterPlanStatus() == RosterPlanStatus.MODIFIED
        && rosterPlan.getRosterPlanStatus() == RosterPlanStatus.QUEUED;
  }
}
