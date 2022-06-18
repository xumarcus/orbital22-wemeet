package com.orbital22.wemeet.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile({"development", "test"})
public class DevelopmentCORSConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(@NotNull CorsRegistry corsRegistry) {
    // Enable CORS for frontend debugging
    corsRegistry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000") // Localhost
        .allowCredentials(true) // Persist session in debugging
        .allowedMethods("*"); // For testing
  }
}
