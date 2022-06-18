package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import com.orbital22.wemeet.util.HasDateTimeRange;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@NoArgsConstructor
public class DateTimeRangeValidator
    implements ConstraintValidator<DateTimeRangeConstraint, HasDateTimeRange> {
  @Override
  public boolean isValid(HasDateTimeRange dateTimeRange, ConstraintValidatorContext ctx) {
    return HasDateTimeRange.isValid(dateTimeRange);
  }
}
