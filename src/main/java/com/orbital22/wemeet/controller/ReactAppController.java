package com.orbital22.wemeet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactAppController {
  @RequestMapping(value = { "/", "/{head:[\\w\\-]+}", "/{head:^(?!api$).*$}/*/{tail:[\\w\\-]+}","/error"  })
  String index(@PathVariable String head, @PathVariable String tail) {
    return "/index.html";
  }
}