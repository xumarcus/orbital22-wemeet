package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.HelloResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    @NonNull DataSource dataSource;

    @GetMapping("/register") // TODO dto?
    public void register(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password
    ) {

    }
}
