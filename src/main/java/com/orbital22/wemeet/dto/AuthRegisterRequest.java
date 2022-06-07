package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.annotation.NewEmailConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterRequest {
  @NotNull
  @Email(message = "Valid email address required")
  @NewEmailConstraint
  private String email;

  @NotNull
  @Size(min = 8, message = "Password should have at least 8 characters")
  private String password;
}
