package com.orbital22.wemeet.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
public class AclServiceConfig {
  private final DataSource dataSource;
  private final CacheManager cacheManager;

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
    BasicLookupStrategy lookupStrategy =
        new BasicLookupStrategy(
            dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
    lookupStrategy.setAclClassIdSupported(true);
    return lookupStrategy;
  }

  @Profile("test")
  @Bean
  public JdbcMutableAclService testAclService() {
    JdbcMutableAclService aclService =
        new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    aclService.setAclClassIdSupported(true);
    return aclService;
  }

  @Profile("!test") // PostgresQL
  @Bean
  public JdbcMutableAclService aclService() {
    JdbcMutableAclService aclService =
        new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    aclService.setAclClassIdSupported(true);
    aclService.setClassIdentityQuery("select currval(pg_get_serial_sequence('acl_class', 'id'))");
    aclService.setSidIdentityQuery("select currval(pg_get_serial_sequence('acl_sid', 'id'))");
    return aclService;
  }
}
