package com.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * authservice.
 */
@Service
public class AuthService {
  private final UserService userService;

  @Autowired
  public AuthService(UserService userService) {
    this.userService = userService;
  }

  public UserDetails getAuthenticatedUser(String subject) {
    return userService.loadUserByUsername(subject);
  }
}