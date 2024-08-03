package com.app.user.controller.dto;

import com.app.user.entity.User;
import com.app.user.security.Role;

public record UserDto(
    Long id,
    String username,
    String cpf,
    String email,
    String password,
    String phone,
    Role role,
    String createdBy,
    String modifiedBy
) {
  public static UserDto fromEntity(User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getCpf(),
        user.getEmail(),
        user.getPassword(),
        user.getPhone(),
        user.getRole(),
        user.getCreatedBy(),
        user.getModifiedBy()
    );
  }
}