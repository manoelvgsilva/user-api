package com.app.user.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * authservice.
 */
@Service
public class AuthService {
  private final UserRedisService userRedisService;

  @Autowired
  public AuthService(UserRedisService userRedisService) {
    this.userRedisService = userRedisService;
  }

  /**
   * getauthenticateuser.
   *
   * @param subject the subject
   * @return subject
   */
  public UserDetails getAuthenticatedUser(String subject) {
    Optional<UserDetails> userDetailsFromRedis = Optional
        .ofNullable(userRedisService.getUserByUserEmail(subject));
    if (userDetailsFromRedis.isPresent()) {
      return userDetailsFromRedis.get();
    }

    return userRedisService.loadUserByUsername(subject);
  }
}