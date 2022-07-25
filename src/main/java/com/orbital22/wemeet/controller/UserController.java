package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.UserPostRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
  ResponseEntity<?> post(
      @RequestBody @Valid UserPostRequest request, PersistentEntityResourceAssembler assembler) {
    User user = userService.post(request);
    EntityModel<Object> resource = assembler.toFullResource(user);
    return ResponseEntity.created(resource.getLink(IanaLinkRelations.SELF).orElseThrow().toUri())
        .body(resource);
  }
}
