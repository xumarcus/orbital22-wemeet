package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.dto.GenericAPIResponse;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public GenericAPIResponse<String> register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
        String email = authRegisterRequest.getEmail();
        String password = authRegisterRequest.getPassword();
        userService.register(email, password);
        log.info(String.format("Email [%s] registered", email));
        return new GenericAPIResponse<>(email);
    }
}
