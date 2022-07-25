package com.orbital22.wemeet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Set;

@Projection(
    name = "timeSlotProjection",
    types = {TimeSlot.class})
public interface TimeSlotProjection {
  int getId();

  int getCapacity();

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      timezone = "UTC")
  LocalDateTime getStartDateTime();

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
      timezone = "UTC")
  LocalDateTime getEndDateTime();

  Set<TimeSlotUserInfoProjection> getTimeSlotUserInfos();
}
