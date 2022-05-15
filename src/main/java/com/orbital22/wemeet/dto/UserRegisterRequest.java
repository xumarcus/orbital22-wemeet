package com.orbital22.wemeet.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserRegisterRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
