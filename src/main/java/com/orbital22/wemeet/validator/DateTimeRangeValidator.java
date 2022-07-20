package com.orbital22.wemeet.validator;

import com.orbital22.wemeet.annotation.DateTimeRangeConstraint;
import com.orbital22.wemeet.util.IDateTimeRange;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@NoArgsConstructor
public class DateTimeRangeValidator
    implements ConstraintValidator<DateTimeRangeConstraint, IDateTimeRange> {
  @Override
  public boolean isValid(IDateTimeRange dateTimeRange, ConstraintValidatorContext ctx) {
    return dateTimeRange.isValid();
  }
}
