package com.orbital22.wemeet.annotation;

import com.orbital22.wemeet.validator.DateTimeRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeRangeValidator.class)
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeRangeConstraint {
  String message() default "Date range is not valid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
