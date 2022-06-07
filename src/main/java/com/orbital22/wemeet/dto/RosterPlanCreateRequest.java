package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
public class RosterPlanCreateRequest {
  @NotNull @NonNull @Valid List<@Email(message = "Valid email address required") String> emails;

  @NotNull @NonNull @Valid List<@DateTimeRangeConstraint TimeSlotDto> timeSlotDtos;

  @NotBlank String title;
}
