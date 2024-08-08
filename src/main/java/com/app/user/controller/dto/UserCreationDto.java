package com.app.user.controller.dto;

import com.app.user.entity.User;
import com.app.user.security.Role;

/**
 * usercreationdto.
 *
 * @param username the username
 * @param cpf the cfp
 * @param email the email
 * @param password the password
 * @param phone the phone
 * @param role the role
 */
public record UserCreationDto(
    String username,
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
    return new User(null, username, cpf,  email, password, phone, role);
  }
}