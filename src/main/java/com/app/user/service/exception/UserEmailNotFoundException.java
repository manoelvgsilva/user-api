package com.app.user.service.exception;

/**
 * useremailnotfoundexception.
 */
public class UserEmailNotFoundException extends RuntimeException {
  public UserEmailNotFoundException(String email) {
    super("Email não localizado: " + email);
  }
}
