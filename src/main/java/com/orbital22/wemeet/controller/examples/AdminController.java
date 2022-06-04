package com.orbital22.wemeet.controller.examples;

import com.orbital22.wemeet.dto.PODResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

/** Example */
@Profile("development")
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    DataSource dataSource;

  @GetMapping()
  @PreAuthorize("hasRole('ADMIN')")
  public PODResponse<String> admin() {
        try (Connection connection = dataSource.getConnection()) {
            String data = connection.getMetaData().getDatabaseProductVersion();
            return new PODResponse<>(data);
        } catch (Exception e) {
            return new PODResponse<>(e.getMessage());
        }
    }
}
