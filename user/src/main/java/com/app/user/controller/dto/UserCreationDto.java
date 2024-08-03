package com.app.user.controller.dto;

import com.app.user.entity.User;
import com.app.user.security.Role;

public record UserCreationDto(
    String username,
    String cpf,
    String email,
    String password,
    String phone,
    Role role
) {
  public User toEntity() {
    return new User(null, username, cpf,  email, password, phone, role);
  }
}