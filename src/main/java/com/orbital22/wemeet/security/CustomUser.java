package com.orbital22.wemeet.security;

import com.orbital22.wemeet.model.User;
import lombok.Getter;

@Getter
public class CustomUser extends org.springframework.security.core.userdetails.User {
  private final int id;

  public CustomUser(User user) {
    super(
        user.getEmail(),
        user.getPassword(),
        user.isEnabled(),
        user.isRegistered(),
        true,
        true,
        user.getAuthorities());
    this.id = user.getId();
  }
}
