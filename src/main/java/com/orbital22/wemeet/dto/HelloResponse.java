package com.orbital22.wemeet.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class HelloResponse {
    @NotNull String hello;
}
