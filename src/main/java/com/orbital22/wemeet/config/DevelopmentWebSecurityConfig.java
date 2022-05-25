package com.orbital22.wemeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("development")
public class DevelopmentWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Redundant check that limits API to authenticated users.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/admin/**", "/actuator/**").hasRole("ADMIN")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll() // TODO how about actuators?
                .and()
                .formLogin()
                .defaultSuccessUrl("/", true);
    }
}
