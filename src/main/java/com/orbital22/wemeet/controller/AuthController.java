package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.dto.GenericAPIResponse;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @PostMapping("/register")
    public GenericAPIResponse<String> register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
        User user = User.builder()
                .email(authRegisterRequest.getEmail())
                .password(passwordEncoder.encode(authRegisterRequest.getPassword()))
                .enabled(true)
                .registered(true)
                .build();
        userService.register(user);
        String data = String.format("Email [%s] is registered successfully.", user.getEmail());
        log.info(data);
        return new GenericAPIResponse<>(data);
    }
}
