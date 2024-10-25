package com.app.user.controller.dto;

import com.app.user.entity.UserRedis;
import com.app.user.security.Role;
import java.time.LocalDate;

/**
 * userredisdto.
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
public record UserRedisDto(
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
  public static UserRedisDto fromEntity(UserRedis user) {
    return new UserRedisDto(
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