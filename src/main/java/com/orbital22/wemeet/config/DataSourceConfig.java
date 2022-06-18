package com.orbital22.wemeet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
  @Value("${spring.datasource.url:}")
  private String dbUrl;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    String localDbUrl = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password";
    config.setJdbcUrl(dbUrl.isEmpty() ? localDbUrl : dbUrl);
    return new HikariDataSource(config);
  }
}
