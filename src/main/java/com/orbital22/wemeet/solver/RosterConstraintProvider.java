package com.orbital22.wemeet.solver;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class RosterConstraintProvider implements ConstraintProvider {
  @Override
  public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
    return new Constraint[] {
      rankConstraint(constraintFactory),
      rejectConstraint(constraintFactory),
      capacityConstraint(constraintFactory)
    };
  }

  private Constraint rankConstraint(ConstraintFactory constraintFactory) {
    // noinspection ConstantConditions
    return constraintFactory
        .forEach(RosterPlanUserPlanningEntity.class)
        .filter(e -> e.penalty() != null)
        .penalize(
            "User rank timeslot", HardSoftScore.ONE_SOFT, RosterPlanUserPlanningEntity::penalty);
  }

  private Constraint rejectConstraint(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(RosterPlanUserPlanningEntity.class)
        .filter(e -> e.penalty() == null)
        .penalize("User reject timeslot", HardSoftScore.ONE_HARD);
  }

  private Constraint capacityConstraint(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(RosterPlanUserPlanningEntity.class)
        .groupBy(RosterPlanUserPlanningEntity::getTimeSlot, ConstraintCollectors.count())
        .groupBy(((timeSlot, count) -> count - timeSlot.getCapacity()))
        .filter(exceed -> exceed > 0)
        .groupBy(ConstraintCollectors.sum(exceed -> exceed))
        .penalize("Full timeSlot", HardSoftScore.ONE_HARD, exceed -> exceed);
  }
}
