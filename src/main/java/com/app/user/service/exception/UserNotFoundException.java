package com.app.user.service.exception;

/**
 * usernotfoundexception.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String s) {
    super("Usuário não encontrado!");
  }
}