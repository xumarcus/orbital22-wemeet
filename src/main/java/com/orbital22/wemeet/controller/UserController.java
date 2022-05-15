package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.UserRegisterRequest;
import com.orbital22.wemeet.exception.UserAlreadyExistsException;
import com.orbital22.wemeet.model.Authority;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest userRegisterRequest) {
        Authority authority = new Authority("ROLE_USER");
        User user = User.builder()
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .enabled(true)
                .authorities(Collections.singleton(authority))
                .build();
        try {
            userService.register(user);
            return "OK";    // TODO
        } catch (UserAlreadyExistsException e) {
            return e.getMessage();
        }
    }
}
