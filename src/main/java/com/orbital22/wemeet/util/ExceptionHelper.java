package com.orbital22.wemeet.util;

import org.jetbrains.annotations.Nullable;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;

public interface ExceptionHelper {
  static RepositoryConstraintViolationException from(
          Object object, @Nullable String field, String errorCode) {
    return from(object, field, errorCode, null);
  }

  static RepositoryConstraintViolationException from(
          Object object, @Nullable String field, String errorCode, @Nullable String defaultMessage) {
    Errors errors = new MapBindingResult(new HashMap<>(), object.getClass().getName());
    Object[] errorArgs = new Object[] {object};

    if (field != null) {
      errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
    } else {
      errors.reject(errorCode, errorArgs, defaultMessage);
    }

    return new RepositoryConstraintViolationException(errors);
  }
}
