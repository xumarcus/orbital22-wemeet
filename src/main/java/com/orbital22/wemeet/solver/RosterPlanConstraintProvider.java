package com.orbital22.wemeet.solver;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.util.Objects;

public class RosterPlanConstraintProvider implements ConstraintProvider {
  private static int fromBoolean(Boolean x) {
    assert (x != null);
    return x ? 1 : 0;
  }

  @Override
  public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
    return new Constraint[] {
      assignmentRankConstraint(constraintFactory),
      userSizeConstraint(constraintFactory),
      timeSlotCapacityConstraint(constraintFactory)
    };
  }

  private Constraint assignmentRankConstraint(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(Assignment.class)
        .filter(
            assignment -> Objects.requireNonNull(assignment.getConsidered(), "Must be initialized"))
        .penalize("User rank timeslot", HardSoftScore.ONE_SOFT, Assignment::getRank);
  }

  private Constraint userSizeConstraint(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(Assignment.class)
        .groupBy(
            Assignment::getUser,
            ConstraintCollectors.sum(assignment -> fromBoolean(assignment.getConsidered())))
        .join(RosterPlanSolution.RosterPlanSolutionConfiguration.class)
        .filter((user, count, config) -> !config.getAllocationCountRange().contains(count))
        .penalize("Number of time slots for user must be in range", HardSoftScore.ONE_HARD);
  }

  private Constraint timeSlotCapacityConstraint(ConstraintFactory constraintFactory) {
    return constraintFactory
        .forEach(Assignment.class)
        .filter(
            assignment -> Objects.requireNonNull(assignment.getConsidered(), "Must be initialized"))
        .groupBy(Assignment::getTimeSlot, ConstraintCollectors.count())
        .groupBy(((timeSlot, count) -> count - timeSlot.getCapacity()))
        .filter(exceed -> exceed > 0)
        .penalize("Full timeslot", HardSoftScore.ONE_HARD);
  }
}
