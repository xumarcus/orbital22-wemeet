package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.HelloResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/hello")
@RequiredArgsConstructor
public class HelloController {
    @NonNull
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
