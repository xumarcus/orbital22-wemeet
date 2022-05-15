package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.AdminResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {
    DataSource dataSource;

    @GetMapping()
    @Secured("ROLE_ADMIN")
    public AdminResponse hello() {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getSQLKeywords();
            return new AdminResponse(url);
        } catch (Exception e) {
            return new AdminResponse(e.getMessage());
        }
    }
}
