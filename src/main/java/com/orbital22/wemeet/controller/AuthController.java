package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<EntityModel<User>> register(
      @Valid @RequestBody AuthRegisterRequest request) {
    User user = userService.register(request).orElseThrow();
    EntityModel<User> resources = EntityModel.of(user);
    resources.add(
        linkTo(methodOn(AuthController.class).register(request)).withSelfRel(),
        linkTo(AuthController.class).slash(user.getId()).withSelfRel());
    return ResponseEntity.ok(resources);
  }
}
