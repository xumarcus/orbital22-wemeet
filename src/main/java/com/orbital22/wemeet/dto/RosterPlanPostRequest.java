package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.User;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

// TODO can factor constraints on allocationCount into entity after redesign?
@Data
@FieldNameConstants
public class RosterPlanPostRequest {
  @NotBlank
  private String title;

  @Nullable
  private RosterPlan parent;

  @Nullable
  private User owner;

  @Nullable
  @Positive
  private Integer minAllocationCount;

  @Nullable
  @Positive
  private Integer maxAllocationCount;
}
