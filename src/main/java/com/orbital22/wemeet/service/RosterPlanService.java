package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private final LocalValidatorFactoryBean validator;

  public void validate(RosterPlanDto e) {
    Errors errors = new BeanPropertyBindingResult(e, e.getClass().getName());
    validator.validate(e, errors);
    if (errors.hasErrors()) {
      throw new RepositoryConstraintViolationException(errors);
    }
  }
}
