package com.app.user.service.exception;

/**
 * usernotfoundexception.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuário não encontrado!");
  }
}