package com.orbital22.wemeet.security;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
@AllArgsConstructor
public class ValidationHelper<T> {
  private final LocalValidatorFactoryBean validator;

  public void validate(T e) {
    Errors errors = new BeanPropertyBindingResult(e, e.getClass().getName());
    validator.validate(e, errors);
    if (errors.hasErrors()) {
      throw new RepositoryConstraintViolationException(errors);
    }
  }
}
