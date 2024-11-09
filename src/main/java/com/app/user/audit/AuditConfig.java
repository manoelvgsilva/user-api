package com.app.user.audit;

import com.app.user.entity.User;
import com.app.user.entity.UserRedis;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * auditconfig.
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AuditConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareUser();
  }

  /**
   * modelmapper.
   *
   * @return modelmapper
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(User.class, UserRedis.class).addMappings(mapper -> {
      mapper.map(User::getEmail, UserRedis::setEmail);
      mapper.map(User::getPassword, UserRedis::setPassword);
    });
    return modelMapper;
  }
}