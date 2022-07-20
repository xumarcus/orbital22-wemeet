package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.security.CustomUser;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@BasePathAwareController
@AllArgsConstructor
public class UserMeController {
  @GetMapping("/users/me")
  @PreAuthorize("#authentication != null and #authentication.isAuthenticated()")
  String me(Authentication authentication) {
    CustomUser customUser = (CustomUser) authentication.getPrincipal();
    return String.format("forward:/api/users/%d", customUser.getUser().getId());
  }
}
