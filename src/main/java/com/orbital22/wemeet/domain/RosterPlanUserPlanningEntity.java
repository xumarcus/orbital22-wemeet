package com.orbital22.wemeet.domain;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/** Assign timeslots on per RosterPlan basis */
@PlanningEntity
public class RosterPlanUserPlanningEntity {
  @PlanningId private int id;

  private RosterPlan rosterPlan;

  @PlanningVariable(valueRangeProviderRefs = "timeSlots")
  private TimeSlot timeSlot;
}
