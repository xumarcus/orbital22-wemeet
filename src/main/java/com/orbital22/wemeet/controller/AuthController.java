package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AuthRegisterRequest;
import com.orbital22.wemeet.dto.GenericAPIResponse;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.UserRepository;
import com.orbital22.wemeet.service.CustomUserDetailsService;
import com.orbital22.wemeet.service.CustomUserPrincipal;
import com.orbital22.wemeet.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    private UserRepository userRepository;

    @PostMapping("/register")
    public GenericAPIResponse<String> register(@Valid @RequestBody AuthRegisterRequest authRegisterRequest) {
        String email = authRegisterRequest.getEmail();
        String password = authRegisterRequest.getPassword();
        userService.register(email, password);
        log.info(String.format("Email [%s] registered", email));
        return new GenericAPIResponse<>(email);
    }

    @Transactional
    @GetMapping("/id")
    public User id(Principal principal) {
        return Optional
                .ofNullable(principal)
                .map(Principal::getName)
                .flatMap(userRepository::findByEmail)
                .orElse(null);
    }
}
