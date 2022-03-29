package com.corradowaver.servermodule.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


const val ACCOUNTS_CACHE_NAME = "accounts"

@Configuration
@EnableCaching
class CacheConfig {

  @Bean
  fun cacheManager(): CacheManager = ConcurrentMapCacheManager(ACCOUNTS_CACHE_NAME)

}
