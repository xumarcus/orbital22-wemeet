package com.orbital22.wemeet.domain;

import com.orbital22.wemeet.model.TimeSlot;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.Set;

@Getter
// @Setter(AccessLevel.NONE): Generated from solver
@RequiredArgsConstructor
@PlanningSolution
public class RosterPlanningSolution {
  @NonNull
  @ValueRangeProvider(id = "timeSlots")
  @ProblemFactCollectionProperty
  private Set<TimeSlot> timeSlots;

  @NonNull @PlanningEntityCollectionProperty
  private Set<RosterPlanUserPlanningEntity> rosterPlanUsers;

  @PlanningScore private HardSoftScore score;
}
