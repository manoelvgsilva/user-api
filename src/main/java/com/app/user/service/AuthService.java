package com.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * authservice.
 */
@Service
public class AuthService {
  private final UserService userService;
  private final UserRedisService userRedisService;

  @Autowired
  public AuthService(UserService userService, UserRedisService userRedisService) {
    this.userService = userService;
    this.userRedisService = userRedisService;
  }

  public UserDetails getAuthenticatedUser(String subject) {
    Optional<UserDetails> userDetailsFromRedis = Optional.ofNullable(userRedisService.getUserByUserEmail(subject));
    if (userDetailsFromRedis.isPresent()) {
      return userDetailsFromRedis.get();
    }

    return userService.loadUserByUsername(subject);
  }
}