package com.orbital22.wemeet.service;

import com.orbital22.wemeet.mapper.TimeSlotUserInfoAssignmentMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.RosterPlanUserInfo;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.solver.Assignment;
import com.orbital22.wemeet.solver.RosterPlanSolution;
import lombok.AllArgsConstructor;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SolverService {
  private final RosterPlanRepository rosterPlanRepository;
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;
  private final RosterPlanService rosterPlanService;
  private final UserService userService;
  private final SolverManager<RosterPlanSolution, Integer> solverManager;

  @NotNull
  private RosterPlanSolution createRosterPlanSolutionFrom(RosterPlan rosterPlan) {
    return RosterPlanSolution.builder()
        .id(rosterPlan.getId())
        .owner(rosterPlan.getOwner())
        .assignments(
            rosterPlan.getRosterPlanUserInfos().stream()
                .filter(rosterPlanUserInfo -> !rosterPlanUserInfo.isLocked())
                .map(RosterPlanUserInfo::getUser)
                .flatMap(
                    user ->
                        timeSlotUserInfoRepository
                            .findByRosterPlanAndUser(rosterPlan, user)
                            .stream()
                            .map(
                                TimeSlotUserInfoAssignmentMapper.INSTANCE
                                    ::timeSlotUserInfoToAssignment))
                .collect(Collectors.toSet()))
        .build();
  }

  private void updateFromAssignments(RosterPlanSolution solution) {
    timeSlotUserInfoRepository.saveAll(
        () ->
            solution.getAssignments().stream()
                .map(TimeSlotUserInfoAssignmentMapper.INSTANCE::assignmentToTimeSlotUserInfo)
                .iterator());
  }

  private void lockUsersWithPicks(RosterPlanSolution solution, RosterPlan rosterPlan) {
    rosterPlanUserInfoRepository.saveAll(
        () ->
            solution.getAssignments().stream()
                .filter(
                    assignment ->
                        Objects.requireNonNull(assignment.getConsidered(), "Must be initialized"))
                .map(Assignment::getUser)
                .map(
                    user ->
                        rosterPlanUserInfoRepository
                            .findByRosterPlanAndUser(rosterPlan, user)
                            .orElseThrow())
                .peek(rosterPlanUserInfo -> rosterPlanUserInfo.setLocked(true))
                .iterator());
  }

  @NotNull
  public void updateRosterPlanWith(RosterPlanSolution solution) {
    // `findById` requires authentication
    // userService.setSessionUser(solution.getOwner());

    // Update is async, new `RosterPlan` is safer even if it is supposed to be immutable.
    RosterPlan rosterPlan = rosterPlanRepository.findById(solution.getId()).orElseThrow();

    updateFromAssignments(solution);
    lockUsersWithPicks(solution, rosterPlan);

    rosterPlan.setSolved(true);
    rosterPlanService.justSave(rosterPlan);
  }

  public void solve(RosterPlan rosterPlan) {
    rosterPlanService.deepCopy(rosterPlan.getParent(), rosterPlan);
    RosterPlanSolution problem = createRosterPlanSolutionFrom(rosterPlan);
    solverManager.solve(rosterPlan.getId(), problem, this::updateRosterPlanWith);
  }
}
