package com.orbital22.wemeet.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {
    @NotNull
    @Email(message = "Valid email address required")
    private String username;
    @NotNull
    @Min(value = 8, message = "Password length is at least 8 characters")
    private String password;
}
