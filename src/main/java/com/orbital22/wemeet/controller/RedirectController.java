package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.security.CustomUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
  @GetMapping("/api/users/me")
  @PreAuthorize("#authentication != null and #authentication.isAuthenticated()")
  public String me(Authentication authentication) {
    //noinspection SpringMVCViewInspection
    return "/api/users/" + ((CustomUser) authentication.getPrincipal()).getId();
  }
}
