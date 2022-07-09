package com.orbital22.wemeet.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(
    name = "rosterPlanProjection",
    types = {RosterPlan.class})
public interface RosterPlanProjection {
  int getId();

  String getTitle();

  RosterPlan getParent();

  User getOwner();

  Set<TimeSlotProjection> getTimeSlots();

  Set<RosterPlanUserInfoProjection> getRosterPlanUserInfos();

  Boolean getSolved();

  int getMinAllocationCount();

  int getMaxAllocationCount();
}
