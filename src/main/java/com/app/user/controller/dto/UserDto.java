package com.app.user.controller.dto;

import com.app.user.entity.User;
import com.app.user.security.Role;
import java.time.LocalDate;

/**
 * userdto.
 *
 * @param id the id
 * @param username the username
 * @param dataNasc the datanasc
 * @param cpf the cpf
 * @param email the email
 * @param password the password
 * @param phone the phone
 * @param role the role
 */
public record UserDto(
    String id,
    String username,
    LocalDate dataNasc,
    String cpf,
    String email,
    String password,
    String phone,
    Role role
) {

  /**
   * entity.
   *
   * @param user the user
   * @return user
   */
  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getDataNasc(),
        user.getCpf(),
        user.getEmail(),
        user.getPassword(),
        user.getPhone(),
        user.getRole()
    );
  }
}