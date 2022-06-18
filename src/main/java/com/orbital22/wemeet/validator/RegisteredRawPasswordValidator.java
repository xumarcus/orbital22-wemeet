package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.RegisteredRawPasswordConstraint;
import com.orbital22.wemeet.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@NoArgsConstructor
public class RegisteredRawPasswordValidator
    implements ConstraintValidator<RegisteredRawPasswordConstraint, User> {
  @Override
  public boolean isValid(User user, ConstraintValidatorContext ctx) {
    return (user.getRawPassword() != null) == user.isRegistered();
  }
}
