package com.orbital22.wemeet.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.model.MutableAcl;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
  @Bean
  @Override
  public CacheManager cacheManager() {
    javax.cache.CacheManager ehCacheManager = Caching.getCachingProvider().getCacheManager();
    if (ehCacheManager.getCache("aclCache") == null) {
      MutableConfiguration<Integer, MutableAcl> aclCacheConfig = new MutableConfiguration<>();
      ehCacheManager.createCache("aclCache", aclCacheConfig);
    }
    return new JCacheCacheManager(ehCacheManager);
  }
}
