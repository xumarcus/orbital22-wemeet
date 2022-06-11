package com.orbital22.wemeet.annotation;

import com.orbital22.wemeet.validator.RosterPlanStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RosterPlanStatusValidator.class)
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RosterPlanStatusConstraint {
  String message() default "Illegal roster plan status update";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
