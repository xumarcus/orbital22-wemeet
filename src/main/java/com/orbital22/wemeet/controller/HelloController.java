package com.orbital22.wemeet.controller;

import com.orbital22.wemeet.dto.HelloResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @Autowired
    private DataSource dataSource;

    @GetMapping()
    public HelloResponse hello() {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            return new HelloResponse(url);
        } catch (Exception e) {
            return new HelloResponse(e.getMessage());
        }
    }
}
