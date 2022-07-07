package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.UserPostRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RepositoryRestController
@AllArgsConstructor
public class UserController {
  private final LocalValidatorFactoryBean validator;
  private final UserService userService;

  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(validator);
  }

  @PostMapping("/users")
  String post(@RequestBody @Valid UserPostRequest request) {
    User user = userService.post(request);
    return "/api/users/" + user.getId();
  }
}
