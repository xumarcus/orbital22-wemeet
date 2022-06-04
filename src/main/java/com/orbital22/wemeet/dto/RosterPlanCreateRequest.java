package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Data
public class RosterPlanCreateRequest {
  @NotNull @Valid
  List<@Email(message = "Valid email address required") String> emails = Collections.emptyList();

  @NotNull @Valid List<@DateTimeRangeConstraint TimeSlotDto> timeSlotDtos;

  @NotBlank String title;
}
