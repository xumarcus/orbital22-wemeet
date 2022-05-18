package com.orbital22.wemeet.dto;

import com.orbital22.wemeet.annotation.NewEmailConstraint;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {
    @NotNull
    @Email(message = "Valid email address required")
    @NewEmailConstraint
    private String email;

    @NotNull
    @Min(value = 8, message = "Password should have at least 8 characters")
    private String password;
}
