package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@PlanningSolution
public class RosterPlanningSolution {
  private int id;

  // Re-authenticate
  @NotNull private User owner;

  @ValueRangeProvider(id = "timeSlots")
  @ProblemFactCollectionProperty
  private Set<TimeSlot> timeSlots;

  @PlanningEntityCollectionProperty private Set<RosterPlanUserPlanningEntity> rosterPlanUsers;

  @PlanningScore private HardSoftScore score;
}
