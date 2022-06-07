package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.util.DateTimeRange;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Builder
@Data
public class TimeSlotDto implements DateTimeRange {
  @NotNull private LocalDateTime startDateTime;
  @NotNull private LocalDateTime endDateTime;
  @Positive private int capacity;
}
