package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.NewEmailConstraint;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class NewEmailValidator implements ConstraintValidator<NewEmailConstraint, String> {
  private UserRepository userRepository;

  @Override
  public boolean isValid(String email, ConstraintValidatorContext ctx) {
    return !userRepository.existsByEmail(email);
  }
}
