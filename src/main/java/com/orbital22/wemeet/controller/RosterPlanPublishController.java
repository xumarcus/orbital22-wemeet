package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanPublishRequest;
import com.orbital22.wemeet.service.RosterPlanService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@BasePathAwareController
@AllArgsConstructor
public class RosterPlanPublishController {
  private final LocalValidatorFactoryBean validator;
  private final RosterPlanService rosterPlanService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/rosterPlan/publish")
  @ResponseBody // For HTTP status
  void publish(@RequestBody @Valid RosterPlanPublishRequest request) {
    rosterPlanService.publish(request.getChild());
  }
}
