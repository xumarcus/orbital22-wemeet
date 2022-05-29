package com.orbital22.wemeet.solver;

import com.orbital22.wemeet.domain.RosterPlanUserPlanningEntity;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class RosterConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] { dummyConstraint(constraintFactory) };
    }

    private Constraint dummyConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(RosterPlanUserPlanningEntity.class)
                .penalize("Testing", HardSoftScore.ONE_SOFT);
    }
}
