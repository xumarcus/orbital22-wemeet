package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.dto.TimeSlotDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Set;

@Getter
// @Setter(AccessLevel.NONE): Generated from solver
@AllArgsConstructor
@PlanningSolution
public class RosterPlanningSolution {
  @ValueRangeProvider(id = "timeSlotDtos")
  @ProblemFactCollectionProperty
  private Set<TimeSlotDto> timeSlotDtos;

  @PlanningEntityCollectionProperty private Set<RosterPlanUserPlanningEntity> rosterPlanUsers;

  @PlanningScore private HardSoftScore score;
}
