package com.app.user.controller.dto;

import com.app.user.entity.User;
import com.app.user.security.Role;
import java.time.LocalDate;

/**
 * usercreationdto.
 *
 * @param username the username
 * @param dataNasc the datanasc
 * @param cpf the cfp
 * @param email the email
 * @param password the password
 * @param phone the phone
 * @param role the role
 */
public record UserCreationDto(
    String username,
    LocalDate dataNasc,
    String cpf,
    String email,
    String password,
    String phone,
    Role role
) {

  /**
   * toentity.
   *
   * @return entity
   */
  public User toEntity() {
    return new User(null, username, dataNasc, cpf,  email, password, phone,
        role);
  }
}