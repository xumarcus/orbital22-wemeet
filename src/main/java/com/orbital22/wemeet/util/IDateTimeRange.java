package com.orbital22.wemeet.util;

import lombok.NonNull;
import org.apache.commons.lang3.Range;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;

import static org.apache.commons.lang3.Range.between;

public interface IDateTimeRange {
  @NonNull
  LocalDateTime getStartDateTime();

  @NonNull
  LocalDateTime getEndDateTime();

  default boolean isValid() {
    return getStartDateTime().isBefore(getEndDateTime());
  }

  default Range<ChronoLocalDateTime<?>> getChronoLocalDateTimeRange() {
    return between(getStartDateTime(), getEndDateTime());
  }
}
