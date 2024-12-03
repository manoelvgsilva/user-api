package com.app.user.audit;

import com.app.user.entity.UserRedis;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisconfig.
 */
@Configuration
@EnableCaching
@EnableRedisRepositories(basePackages = "com.app.user.repository")
public class RedisConfig {

  /**
   * redistemplate.
   *
   * @param connectionFactory the connectionfactory
   * @return connection
   */
  @Bean
  public RedisTemplate<String, UserRedis> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, UserRedis> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }
}