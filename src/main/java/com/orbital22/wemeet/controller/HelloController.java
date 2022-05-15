package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.HelloResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/hello")
@AllArgsConstructor
public class HelloController {
    DataSource dataSource;

    @GetMapping()
    @Secured(value="AUTHORITY_ADMIN")
    public HelloResponse hello() {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getSQLKeywords();
            return new HelloResponse(url);
        } catch (Exception e) {
            return new HelloResponse(e.getMessage());
        }
    }
}
