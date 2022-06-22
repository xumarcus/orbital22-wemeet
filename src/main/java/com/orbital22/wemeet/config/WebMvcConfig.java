package com.orbital22.wemeet.config;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Configuration
public class WebMvcConfig implements ErrorViewResolver {

  @Override
  public ModelAndView resolveErrorView(
      HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
    if (status == HttpStatus.NOT_FOUND) {
      return new ModelAndView("forward:/index.html");
    }
    return null;
  }
}
