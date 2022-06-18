package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@PlanningEntity
public class Assignment {
  @PlanningId private int id;
  private User user;
  private TimeSlot timeSlot;
  private int rank;

  @PlanningVariable(valueRangeProviderRefs = "booleanValueRange")
  @Builder.Default
  @Nullable
  private Boolean considered = null;
}
