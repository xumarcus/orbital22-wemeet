package com.orbital22.wemeet.util;

import lombok.NonNull;

import java.time.LocalDateTime;

public interface HasDateTimeRange {
  @NonNull
  LocalDateTime getStartDateTime();

  @NonNull
  LocalDateTime getEndDateTime();

  static boolean isValid(HasDateTimeRange dateTimeRange) {
    return dateTimeRange.getStartDateTime().isBefore(dateTimeRange.getEndDateTime());
  }

  default boolean containsRange(HasDateTimeRange other) {
    return getStartDateTime().isBefore(other.getStartDateTime())
        && getEndDateTime().isAfter(other.getEndDateTime());
  }
}
