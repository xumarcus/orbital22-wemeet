package com.orbital22.wemeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Redundant check that limits API to authenticated users.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // dev
                .authorizeHttpRequests()
                .antMatchers("/api/user/register*", "/login*", "/logout*", "/*").permitAll()
                .antMatchers("/api/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin();

    }
}
