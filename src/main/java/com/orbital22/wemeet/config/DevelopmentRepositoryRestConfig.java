package com.orbital22.wemeet.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@Profile({"development", "test"})
@AllArgsConstructor
public class DevelopmentRepositoryRestConfig implements RepositoryRestConfigurer {
  private final LocalValidatorFactoryBean validator;

  /**
   * @see DevelopmentCORSConfig
   */
  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry corsRegistry) {
    // For testing
    config.setExposeRepositoryMethodsByDefault(true);

    // Enable CORS for frontend debugging
    corsRegistry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000") // Localhost
        .allowCredentials(true) // Persist session in debugging
        .allowedMethods("*"); // For testing

    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, corsRegistry);
  }

  @Override
  public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeCreate", validator);
    validatingListener.addValidator("beforeSave", validator);
    RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(validatingListener);
  }
}
