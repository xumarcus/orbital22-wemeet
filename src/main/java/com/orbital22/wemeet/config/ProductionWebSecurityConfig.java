package com.orbital22.wemeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Enables firewall Server should send XSRF-TOKEN cookie and client should reply with X-XSRF-TOKEN
 * cookie.
 *
 * @author xumarcus
 * @version 0.2.1
 */
@Configuration
@Profile("production")
@EnableWebSecurity
public class ProductionWebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        .antMatchers("/api/admin/**", "/actuator/**")
        .hasRole("ADMIN")
        .antMatchers("/api/auth/**", "/public/**", "/static/**", "/*")
        .permitAll()
        .antMatchers("/api/**")
        .authenticated()
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .usernameParameter("email")
        .passwordParameter("password")
        .successHandler(new LoginSuccessHandler())
        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }
}
