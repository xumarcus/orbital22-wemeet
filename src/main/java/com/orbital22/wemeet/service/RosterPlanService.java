package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;

import static com.orbital22.wemeet.enums.RosterPlanStatus.*;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private final RosterPlanRepository rosterPlanRepository;

  /**
   * @see com.orbital22.wemeet.aspect.RosterPlanSaveAspect
   */
  public void handleStatusChange(RosterPlan rosterPlan) {
    RosterPlan prev = rosterPlanRepository.findById(rosterPlan.getId()).orElseThrow();
    if (prev.getRosterPlanStatus() == rosterPlan.getRosterPlanStatus()) return;

    Errors errors = new BeanPropertyBindingResult(rosterPlan, rosterPlan.getClass().getName());
    switch (prev.getRosterPlanStatus()) {
      case MODIFIED:
        switch (rosterPlan.getRosterPlanStatus()) {
          case QUEUED:
            // Fire solver job
            break;
          case SOLVED:
            errors.reject("FORBID_STATUS_CHANGE", new Object[] {MODIFIED, SOLVED}, null);
            break;
        }
        break;
      case QUEUED:
        if (rosterPlan.getRosterPlanStatus() == SOLVED) {
          // Update snapshot
        }
        break;
      case SOLVED:
        if (rosterPlan.getRosterPlanStatus() == QUEUED) {
          errors.reject("FORBID_STATUS_CHANGE", new Object[] {SOLVED, QUEUED}, null);
        }
        break;
    }
    if (errors.hasErrors()) {
      throw new RepositoryConstraintViolationException(errors);
    }
  }
}
