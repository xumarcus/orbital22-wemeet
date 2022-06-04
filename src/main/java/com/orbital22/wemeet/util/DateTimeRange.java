package com.orbital22.wemeet.util;

import lombok.NonNull;

import java.time.LocalDateTime;

public interface DateTimeRange {
  @NonNull
  LocalDateTime getStartDateTime();

  @NonNull
  LocalDateTime getEndDateTime();

  default boolean contains(DateTimeRange other) {
    return getStartDateTime().isBefore(other.getStartDateTime())
        && getEndDateTime().isAfter(other.getEndDateTime());
  }
}
