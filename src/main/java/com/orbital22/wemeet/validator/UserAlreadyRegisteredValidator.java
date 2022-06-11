package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.UserAlreadyRegisteredConstraint;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UserAlreadyRegisteredValidator
    implements ConstraintValidator<UserAlreadyRegisteredConstraint, User> {
  private final UserRepository userRepository;

  @Override
  public boolean isValid(User user, ConstraintValidatorContext ctx) {
    return !(user.isRegistered()
        && userRepository.findByEmail(user.getEmail()).map(User::isRegistered).orElse(false));
  }
}
