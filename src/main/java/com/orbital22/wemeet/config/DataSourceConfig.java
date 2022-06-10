package com.orbital22.wemeet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/postgres?username=postgres&password=password}")
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
    }
}
