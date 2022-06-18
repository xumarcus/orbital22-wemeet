package com.orbital22.wemeet.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class RosterPlanDto {
  @NotBlank private String title;
}
