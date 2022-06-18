package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import com.orbital22.wemeet.util.DateTimeRange;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@NoArgsConstructor
public class DateTimeRangeValidator
    implements ConstraintValidator<DateTimeRangeConstraint, DateTimeRange> {
  @Override
  public boolean isValid(DateTimeRange dateTimeRange, ConstraintValidatorContext ctx) {
    return DateTimeRange.isValid(dateTimeRange);
  }
}
