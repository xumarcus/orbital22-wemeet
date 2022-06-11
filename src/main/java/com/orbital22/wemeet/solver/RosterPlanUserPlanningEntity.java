package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.dto.TimeSlotDto;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.Map;

@Builder
@Data
@PlanningEntity
public class RosterPlanUserPlanningEntity {
  @PlanningId private int id;

  @PlanningVariable(valueRangeProviderRefs = "timeSlotDtos")
  private TimeSlotDto timeSlotDto;

  @NonNull private Map<TimeSlotDto, Integer> rankMap;

  @Nullable
  public Integer penalty() {
    return rankMap.get(timeSlotDto);
  }
}
