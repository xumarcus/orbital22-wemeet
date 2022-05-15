package com.orbital22.wemeet.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/hello")
@AllArgsConstructor
public class HelloController {
    @GetMapping()
    @Secured("ROLE_USER")
    public String hello(Principal principal) {
        return String.format("Hello %s", principal.getName());
    }
}
