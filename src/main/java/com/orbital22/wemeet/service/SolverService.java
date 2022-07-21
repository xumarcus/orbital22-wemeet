package com.orbital22.wemeet.service;

import com.orbital22.wemeet.mapper.RosterPlanRosterPlanSolutionConfigurationMapper;
import com.orbital22.wemeet.mapper.TimeSlotUserInfoAssignmentMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.RosterPlanUserInfoRepository;
import com.orbital22.wemeet.repository.TimeSlotUserInfoRepository;
import com.orbital22.wemeet.solver.Assignment;
import com.orbital22.wemeet.solver.RosterPlanSolution;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SolverService {
  private final RosterPlanRepository rosterPlanRepository;
  private final RosterPlanUserInfoRepository rosterPlanUserInfoRepository;
  private final TimeSlotUserInfoRepository timeSlotUserInfoRepository;
  private final RosterPlanService rosterPlanService;
  private final SolverManager<RosterPlanSolution, Integer> solverManager;

  @NotNull
  private RosterPlanSolution createRosterPlanSolutionFrom(RosterPlan rosterPlan) {
    Set<Assignment> assignments =
        rosterPlan.getRosterPlanUserInfos().stream()
            .flatMap(
                rosterPlanUserInfo ->
                    // TODO Optimize (N+1) DB calls?

                    timeSlotUserInfoRepository
                        .findByRosterPlanAndUser(rosterPlan, rosterPlanUserInfo.getUser())
                        .stream()
                        .filter(TimeSlotUserInfo::isAvailable)
                        .map(
                            TimeSlotUserInfoAssignmentMapper.INSTANCE::timeSlotUserInfoToAssignment)
                        .peek(
                            assignment ->
                                assignment.setUserLockedForRosterPlan(
                                    rosterPlanUserInfo.isLocked())))
            .collect(Collectors.toSet());

    return RosterPlanSolution.builder()
        .id(rosterPlan.getId())
        .owner(rosterPlan.getOwner())
        .assignments(assignments)
        .rosterPlanSolutionConfiguration(
            RosterPlanRosterPlanSolutionConfigurationMapper.INSTANCE
                .rosterPlanToRosterPlanSolutionConfiguration(rosterPlan))
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

  public void updateRosterPlanWith(RosterPlanSolution solution) {
    // `findById` requires authentication
    // userService.setSessionUser(solution.getOwner());

    // Attach entity
    RosterPlan rosterPlan = rosterPlanRepository.findById(solution.getId()).orElseThrow();

    updateFromAssignments(solution);
    lockUsersWithPicks(solution, rosterPlan);

    rosterPlan.setSolved(true);
    rosterPlan.setMinAllocationCount(
        solution.getRosterPlanSolutionConfiguration().getMinAllocationCount());
    rosterPlan.setMaxAllocationCount(
        solution.getRosterPlanSolutionConfiguration().getMaxAllocationCount());
    rosterPlanRepository.save(rosterPlan);
  }

  public void solve(@NotNull RosterPlan rosterPlan) {
    assert rosterPlan.getParent() != null;
    rosterPlanService.deepCopy(rosterPlan.getParent(), rosterPlan);
    RosterPlanSolution problem = createRosterPlanSolutionFrom(rosterPlan);
    solverManager.solve(rosterPlan.getId(), problem, this::updateRosterPlanWith);
  }
}
