package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private UserService userService;

  @PostMapping("/register")
  public User register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
    String email = authRegisterRequest.getEmail();
    String password = authRegisterRequest.getPassword();
    return userService.register(email, password);
  }
}
