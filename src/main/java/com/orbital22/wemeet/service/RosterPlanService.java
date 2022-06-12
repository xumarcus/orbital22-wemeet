package com.orbital22.wemeet.service;

import com.orbital22.wemeet.enums.RosterPlanStatus;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private final RosterPlanRepository rosterPlanRepository;

  public boolean canSolve(RosterPlan rosterPlan) {
    if (rosterPlan.getId() == 0) return false;

    RosterPlan prev = rosterPlanRepository.findById(rosterPlan.getId()).orElseThrow();
    return prev.getRosterPlanStatus() == RosterPlanStatus.MODIFIED
        && rosterPlan.getRosterPlanStatus() == RosterPlanStatus.QUEUED;
  }

  public RosterPlan createSnapshotOf(RosterPlan rosterPlan) {
    return rosterPlan;
  }

  public void solve(RosterPlan rosterPlan) {}
}
