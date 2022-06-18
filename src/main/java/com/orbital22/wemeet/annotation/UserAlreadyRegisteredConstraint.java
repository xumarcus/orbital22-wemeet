package com.orbital22.wemeet.annotation;

import com.orbital22.wemeet.validator.UserAlreadyRegisteredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserAlreadyRegisteredValidator.class)
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAlreadyRegisteredConstraint {
  String message() default "Cannot unregister a registered user.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
