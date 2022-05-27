package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.domain.RosterPlanningSolution;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/roster")
public class RosterController {
    private SolverManager<RosterPlanningSolution, ?> solverManager;

    /*
    @PostMapping("/solve")
    public RosterPlanningSolution solve(?) {
        SolverJob<RosterPlanningSolution, ?> solverJob = solverManager.solve(?, ?);
    }
    */
}
