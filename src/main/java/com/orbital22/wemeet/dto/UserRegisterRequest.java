package com.orbital22.wemeet.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterRequest {
    @NotNull
    @Email
    private String username;
    @NotNull
    @Min(8)
    private String password;
}
