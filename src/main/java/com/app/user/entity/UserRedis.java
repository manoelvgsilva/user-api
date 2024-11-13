package com.app.user.entity;

import com.app.user.security.Role;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidade UserRedis para armazenamento no Redis.
 */
@Primary
@RedisHash("userredis")
public class UserRedis implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Indexed
  private String id;

  @Indexed
  private String username;

  private LocalDate dataNasc;

  @Indexed
  private String email;

  @Indexed
  private String cpf;

  private String password;

  @Indexed
  private String phone;

  private Role role;

  private String roll;

  public UserRedis() {}

  /**
   * userredis.
   *
   * @param id the id
   * @param username the username
   * @param dataNasc the datanasc
   * @param password the password
   * @param cpf the cpf
   * @param phone the phone
   * @param email the email
   * @param role the role
   */
  public UserRedis(String id, String username, LocalDate dataNasc,
                   String password, String cpf, String phone,
                   String email, Role role) {
    this.id = id;
    this.username = username;
    this.dataNasc = dataNasc;
    this.cpf = cpf;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public LocalDate getDataNasc() {
    return dataNasc;
  }

  public void setDataNasc(LocalDate dataNasc) {
    this.dataNasc = dataNasc;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getRoll() {
    return roll;
  }

  public void setRoll(String roll) {
    this.roll = roll;
  }

  /**
   * Retorna as autoridades do usuário com base no seu papel (role).
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getEmail()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserRedis user = (UserRedis) o;
    return Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(dataNasc, user.dataNasc)
        && Objects.equals(cpf, user.cpf)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && Objects.equals(phone, user.phone)
        && Objects.equals(role, user.role);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, dataNasc, cpf, email, password, phone, role);
  }
}
