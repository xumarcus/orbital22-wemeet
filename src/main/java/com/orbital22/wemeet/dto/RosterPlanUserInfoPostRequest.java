package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.model.RosterPlan;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RosterPlanUserInfoPostRequest {
  @NotBlank @Email private String email;
  @NotNull private RosterPlan rosterPlan;
  private boolean locked;
}
