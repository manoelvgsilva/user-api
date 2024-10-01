package com.app.user.security;

/**
 * role.
 */
public enum Role {
  ADMIN("ROLE_ADMIN"),
  MANAGER("ROLE_MANAGER"),
  USER("ROLE_USER");

  private final String email;

  Role(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}