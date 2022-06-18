package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.PODResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/** Example */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/hello")
public class HelloController {
  @GetMapping()
  public PODResponse<String> hello(Principal principal) {
    return new PODResponse<>(String.format("Hello %s", principal.getName()));
  }
}
