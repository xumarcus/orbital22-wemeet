package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.GenericAPIResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Example
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    DataSource dataSource;

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public GenericAPIResponse<String> test() {
        try (Connection connection = dataSource.getConnection()) {
            String data = connection.getMetaData().getDatabaseProductVersion();
            return new GenericAPIResponse<>(data);
        } catch (Exception e) {
            return new GenericAPIResponse<>(e.getMessage());
        }
    }
}
