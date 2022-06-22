package com.orbital22.wemeet.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "rosterPlanUserInfoProjection", types = {RosterPlanUserInfo.class})
public
interface RosterPlanUserInfoProjection {
  int getId();
  RosterPlan getRosterPlan();
  User getUser();
  boolean isLocked();

  @Value("#{target.getUser().getEmail()}")
  String getEmail();
}
