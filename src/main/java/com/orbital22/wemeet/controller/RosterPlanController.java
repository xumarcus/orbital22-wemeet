package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.security.CustomUser;
import com.orbital22.wemeet.service.RosterPlanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/roster-plan")
public class RosterPlanController {
  private RosterPlanService rosterPlanService;

  @PostMapping
  public RosterPlan create(
      @Valid @RequestBody RosterPlanCreateRequest request, Principal principal) {
    return rosterPlanService.create(request, ((CustomUser) principal).getId());
  }

  // private SolverManager<RosterPlanningSolution, ?> solverManager;

  /*
  @PostMapping("/solve")
  public RosterPlanningSolution solve() {
      SolverJob<RosterPlanningSolution, ?> solverJob = solverManager.solve(?, ?);
  }
  */
}
