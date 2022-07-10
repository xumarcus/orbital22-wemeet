package com.orbital22.wemeet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ReactAppController {
  @RequestMapping(
      value = {"/{head:[\\w\\-]+}", "/{head:^(?!api$).*$}/*/{tail:[\\w\\-]+}", "/error"})
  String index(
      @PathVariable Optional<String> head,
      @PathVariable Optional<String> tail,
      HttpServletRequest request) {
    return "/index.html";
  }
}
