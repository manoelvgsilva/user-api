package com.app.user.exception;

/**
 * usernotfoundexception.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuário não encontrado!");
  }
}