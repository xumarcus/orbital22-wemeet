package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.RosterPlanStatusConstraint;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.orbital22.wemeet.enums.RosterPlanStatus.*;

@Component
@AllArgsConstructor
public class RosterPlanStatusValidator
    implements ConstraintValidator<RosterPlanStatusConstraint, RosterPlan> {
  private final RosterPlanRepository rosterPlanRepository;

  @Override
  public boolean isValid(RosterPlan rosterPlan, ConstraintValidatorContext ctx) {
    if (rosterPlan.getId() != 0) {
      RosterPlan prev = rosterPlanRepository.findById(rosterPlan.getId()).orElseThrow();
      return !(prev.getRosterPlanStatus() == MODIFIED && rosterPlan.getRosterPlanStatus() == SOLVED)
          && !(prev.getRosterPlanStatus() == SOLVED && rosterPlan.getRosterPlanStatus() == QUEUED);
    }
    return true;
  }
}
