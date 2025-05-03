package com.app.user.controller.dto;

import com.app.user.entity.UserRedis;
import com.app.user.security.Role;
import java.time.LocalDate;

/**
 * usercreationdto.
 *
 * @param typePerson the typeperson
 * @param username the username
 * @param dataNasc the datanasc
 * @param cpf the cfp
 * @param email the email
 * @param password the password
 * @param phone the phone
 * @param role the role
 */
public record UserRedisCreationDto(
        String typePerson,
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
  public UserRedis toEntity() {
    return new UserRedis(null, typePerson, username, dataNasc, password, cpf, phone, email,
        role);
  }
}
