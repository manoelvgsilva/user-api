package com.app.user.security;

/**
 * role.
 */
public enum Role {
  ADMIN("ROLE_ADMIN"),
  MANAGER("ROLE_MANAGER"),
  USER("ROLE_USER");

  private final String username;

  Role(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}