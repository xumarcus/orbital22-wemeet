package com.orbital22.wemeet.service;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.solver.RosterPlanningSolution;
import lombok.AllArgsConstructor;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

@Service
@Transactional
@AllArgsConstructor
public class SolverService {
  private final RosterPlanRepository rosterPlanRepository;
  private final RosterPlanService rosterPlanService;
  private final UserService userService;
  private final SolverManager<RosterPlanningSolution, Integer> solverManager;

  @NotNull
  private void updateRosterPlanWith(RosterPlanningSolution solution) {
    // `findById` requires authentication
    userService.setSessionUser(solution.getOwner());

    // Update is async, new `RosterPlan` is safer even if it is supposed to be immutable.
    RosterPlan rosterPlan = rosterPlanRepository.findById(solution.getId()).orElseThrow();

    userService.updateTimeSlotUserInfosFromSolution(solution);
    userService.updateRosterPlanUserInfosFromSolution(solution, rosterPlan);

    rosterPlan.setSolved(true);
    rosterPlanService.justSave(rosterPlan);
  }

  public void solve(RosterPlan rosterPlan) {
    rosterPlanService.deepCopy(rosterPlan.getParent(), rosterPlan);
    RosterPlanningSolution problem = userService.createRosterPlanningSolutionFrom(rosterPlan);
    solverManager.solve(rosterPlan.getId(), problem, this::updateRosterPlanWith);
  }
}
