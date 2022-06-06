package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
public class RegisterController {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/api/users/register")
  public ResponseEntity<EntityModel<User>> register(
      @Valid @RequestBody AuthRegisterRequest request) {
    User user = userService.register(request.getEmail(), request.getPassword());
    UsernamePasswordAuthenticationToken authReq =
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    SecurityContextHolder.getContext()
        .setAuthentication(authenticationManager.authenticate(authReq));
    EntityModel<User> resources = EntityModel.of(user);
    resources.add(
        linkTo(methodOn(RegisterController.class).register(request)).withSelfRel(),
        Link.of("/api/users/" + user.getId()));
    return ResponseEntity.ok(resources);
  }
}
