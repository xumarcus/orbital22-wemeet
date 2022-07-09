package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanPostRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.SolverService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RepositoryRestController
@AllArgsConstructor
public class RosterPlanController {
  private final LocalValidatorFactoryBean validator;
  private final RepositoryEntityLinks repositoryEntityLinks;
  private final RosterPlanService rosterPlanService;
  private final SolverService solverService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/rosterPlan")
  ResponseEntity<?> post(@RequestBody @Valid RosterPlanPostRequest request) {
    RosterPlan rosterPlan = rosterPlanService.post(request);

    if (rosterPlan.getParent() != null) {
      solverService.solve(rosterPlan);
    }

    return ResponseEntity.created(
            repositoryEntityLinks.linkToItemResource(rosterPlan, RosterPlan::getId).toUri())
        .build();
  }
}
