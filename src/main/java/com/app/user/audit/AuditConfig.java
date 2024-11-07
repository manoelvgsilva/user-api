package com.app.user.audit;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * auditconfig.
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {
  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareUser();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}