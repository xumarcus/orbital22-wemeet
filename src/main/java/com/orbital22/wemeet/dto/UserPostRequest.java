package com.orbital22.wemeet.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserPostRequest {
  @NotBlank
  @Email
  private String email;

  @Size(min = 8)
  private String rawPassword;
}
