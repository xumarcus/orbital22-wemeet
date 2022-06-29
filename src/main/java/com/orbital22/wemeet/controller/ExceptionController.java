package com.orbital22.wemeet.controller;

import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionController extends RepositoryRestExceptionHandler {
  public ExceptionController(MessageSource messageSource) {
    super(messageSource);
  }
}
