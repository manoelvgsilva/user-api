package com.app.user.entity;

import com.app.user.security.Role;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * user.
 */
@Document(collection = "users")
public class User implements UserDetails {

  @Id
  private ObjectId id;
  private String username;
  private LocalDate dataNasc;
  private String cpf;
  private String email;
  private String password;
  private String phone;
  private Role role;
  private String roll;

  /**
   * user.
   */
  public User() {}

  /**
   * user.
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
  public User(ObjectId id, String username, LocalDate dataNasc, String password,
              String cpf,
              String phone, String email,
              Role role) {
    this.id = id;
    this.username = username;
    this.dataNasc = dataNasc;
    this.cpf = cpf;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.role = role;
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

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getId() {
    return id != null ? id.toHexString() : null;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * username.
   *
   * @return username
   */
  @Override
  public String getUsername() {
    return username;
  }

  public void setDataNasc(LocalDate dataNasc) {
    this.dataNasc = dataNasc;
  }

  public LocalDate getDataNasc() {
    return dataNasc;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCpf() {
    return cpf;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * password.
   *
   * @return password
   */
  @Override
  public String getPassword() {
    return password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhone() {
    return phone;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Role getRole() {
    return role;
  }

  public void setRoll(String roll) {
    this.roll = roll;
  }

  public String getRoll() {
    return roll;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(dataNasc, user.dataNasc)
        && Objects.equals(cpf, user.cpf)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && Objects.equals(phone, user.phone)
        && Objects.equals(role, user.role);
  }

  /**
   * colect.
   *
   * @return collection
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.getEmail()));
  }
}