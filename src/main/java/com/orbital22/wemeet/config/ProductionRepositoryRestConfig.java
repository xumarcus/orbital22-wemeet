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
@Profile("production")
@AllArgsConstructor
public class ProductionRepositoryRestConfig implements RepositoryRestConfigurer {
  private final LocalValidatorFactoryBean validator;

  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config, CorsRegistry cors) {
    config.setExposeRepositoryMethodsByDefault(false);
    RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
  }

  @Override
  public void configureValidatingRepositoryEventListener(
      ValidatingRepositoryEventListener validatingListener) {
    validatingListener.addValidator("beforeCreate", validator);
    validatingListener.addValidator("beforeSave", validator);
    RepositoryRestConfigurer.super.configureValidatingRepositoryEventListener(validatingListener);
  }
}
