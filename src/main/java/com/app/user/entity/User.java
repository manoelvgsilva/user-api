package com.app.user.entity;

import com.app.user.security.Role;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * user.
 */
@Setter
@Document(collection = "users")
public class User implements UserDetails {

  @Getter
  @Id
  @Indexed(unique = true)
  private String id;
  @Getter
  private String typePerson;
  private String username;
  @Getter
  private LocalDate dataNasc;
  @Getter
  @Indexed(unique = true)
  private String cpf;
  @Getter
  @Indexed(unique = true)
  private String email;
  private String password;
  @Getter
  @Indexed(unique = true)
  private String phone;
  @Getter
  private Role role;
  @Getter
  private String roll;

  /**
   * user.
   */
  public User() {}

  /**
   * user.
   *
   * @param id the id
   * @param typePerson the typeperson
   * @param username the username
   * @param dataNasc the datanasc
   * @param password the password
   * @param cpf the cpf
   * @param phone the phone
   * @param email the email
   * @param role the role
   */
  public User(String id, String typePerson, String username, LocalDate dataNasc, String password,
              String cpf,
              String phone, String email,
              Role role) {
    this.id = id;
    this.typePerson = typePerson;
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

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
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
            && Objects.equals(typePerson, user.typePerson)
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