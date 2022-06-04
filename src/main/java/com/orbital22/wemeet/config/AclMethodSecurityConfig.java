package com.orbital22.wemeet.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AclMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
  DataSource dataSource;
  CacheManager cacheManager;

  @Bean
  public AclAuthorizationStrategy aclAuthorizationStrategy() {
    // sysadmin
    return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Bean
  public PermissionGrantingStrategy permissionGrantingStrategy() {
    return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
  }

  @Bean
  public AclCache aclCache() {
    return new SpringCacheBasedAclCache(
        cacheManager.getCache("aclCache"),
        permissionGrantingStrategy(),
        aclAuthorizationStrategy());
  }

  @Bean
  public LookupStrategy lookupStrategy() {
    return new BasicLookupStrategy(
        dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
  }

  @Bean
  public JdbcMutableAclService aclService() {
    JdbcMutableAclService aclService =
        new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    // Postgresql
    aclService.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
    aclService.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");
    return aclService;
  }

  @Override
  protected MethodSecurityExpressionHandler createExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();
    AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
    expressionHandler.setPermissionEvaluator(permissionEvaluator);
    return expressionHandler;
  }
}
