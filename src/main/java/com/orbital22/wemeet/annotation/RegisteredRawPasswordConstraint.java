package com.orbital22.wemeet.annotation;

import com.orbital22.wemeet.validator.RegisteredRawPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegisteredRawPasswordValidator.class)
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisteredRawPasswordConstraint {
  String message() default "Only registered user has password.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
