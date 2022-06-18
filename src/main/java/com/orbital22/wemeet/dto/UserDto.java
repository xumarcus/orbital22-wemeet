package com.orbital22.wemeet.dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
public class UserDto {
  @NotBlank @Email private String email;

  @Nullable
  @Size(min = 8)
  private String password;

  private boolean registered;
}
