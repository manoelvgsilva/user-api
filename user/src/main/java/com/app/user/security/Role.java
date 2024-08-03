package com.app.user.security;

public enum Role {
  ADMIN("ROLE_ADMIN"),
  MANAGER("ROLE_MANAGER"),
  USER("ROLE_USER");

  private final String cpf;

  Role(String cpf) {
    this.cpf = cpf;
  }

  public String getCpf() {
    return cpf;
  }
}