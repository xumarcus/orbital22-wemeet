package com.orbital22.wemeet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public DataSource dataSource() {
        return Optional.ofNullable(dbUrl)
                .filter(url -> !url.isEmpty())
                .map(url -> {
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(url);
                    return new HikariDataSource(config);
                })
                .orElseGet(HikariDataSource::new);
    }
}
