package com.orbital22.wemeet.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
    name = "timeSlotUserInfoProjection",
    types = {TimeSlotUserInfo.class})
public interface TimeSlotUserInfoProjection {
  int getId();

  int getRank();

  boolean isPicked();

  User getUser();
}
