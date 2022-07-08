package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@FieldNameConstants
public class RosterPlanPostRequest {
  @NotBlank
  private String title;

  @Nullable
  private RosterPlan parent;

  @Nullable
  private User owner;
}
