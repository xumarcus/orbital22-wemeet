package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.domain.valuerange.buildin.primboolean.BooleanValueRange;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@PlanningSolution
public class RosterPlanSolution {
  private int id;

  // Re-authenticate?
  @NotNull
  private User owner;

  @ProblemFactProperty
  @NotNull private RosterPlanSolutionConfiguration rosterPlanSolutionConfiguration;

  @ValueRangeProvider(id = "booleanValueRange")
  private final BooleanValueRange booleanValueRange = new BooleanValueRange();

  @PlanningEntityCollectionProperty private Set<Assignment> assignments;

  @PlanningScore private HardSoftScore score;

  @Builder
  @Data
  @AllArgsConstructor
  public static class RosterPlanSolutionConfiguration {
    private int minAllocationCount;
    private int maxAllocationCount;

    public Range<Integer> getAllocationCountRange() {
      return Range.between(getMinAllocationCount(), getMaxAllocationCount());
    }
  }
}
