package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.service.RosterPlanService;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/roster-plan")
public class RosterPlanController {
  private final RepositoryEntityLinks links;
  private final RosterPlanService rosterPlanService;
  private final UserService userService;

  // Consider refactoring to POST /api/users/:id/ownedRosterPlans
  @PostMapping
  public ResponseEntity<?> create(
      @Valid @RequestBody RosterPlanCreateRequest request, Authentication authentication) {
    RosterPlan rosterPlan =
        rosterPlanService.create(
            request, userService.fromAuthentication(authentication).orElseThrow());
    return ResponseEntity.created(links.linkToItemResource(rosterPlan, RosterPlan::getId).toUri())
        .build();
  }

  // private SolverManager<RosterPlanningSolution, ?> solverManager;

  /*
  @PostMapping("/solve")
  public RosterPlanningSolution solve() {
      SolverJob<RosterPlanningSolution, ?> solverJob = solverManager.solve(?, ?);
  }
  */
}
