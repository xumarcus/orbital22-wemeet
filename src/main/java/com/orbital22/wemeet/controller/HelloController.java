package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.GenericAPIResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Example
 */
@RestController
@RequestMapping("/api/hello")
@AllArgsConstructor
public class HelloController {
    @GetMapping()
    public GenericAPIResponse<String> hello(Principal principal) {
        return new GenericAPIResponse<>("Hello" + principal.getName());
    }
}
