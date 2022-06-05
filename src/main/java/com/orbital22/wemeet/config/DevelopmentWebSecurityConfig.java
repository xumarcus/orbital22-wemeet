package com.orbital22.wemeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Disables firewall
 *
 * @author xumarcus
 * @version 0.2.1
 * @see DevelopmentCORSConfig
 */
@Configuration
@Profile({"development", "test"})
@EnableWebSecurity
public class DevelopmentWebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
        // .antMatchers("/api/admin/**", "/actuator/**").hasRole("ADMIN")
        // .antMatchers("/api/auth/**", "/public/**", "/static/**", "/*").permitAll()
        // .antMatchers("/api/**").authenticated()
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
        .disable()
        .cors();
  }
}
