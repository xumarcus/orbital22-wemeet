package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.model.RosterPlan;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;

@Data
@FieldNameConstants
public class RosterPlanPublishRequest {
  @NotNull
  private RosterPlan child;
}
