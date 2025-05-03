package com.app.user.entity;

import com.app.user.security.Role;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@Primary
@RedisHash("userredis")
public class UserRedis implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;

  @Getter
  @Id
  @Indexed
  private String id;

  @Getter
  @Indexed
  private String typePerson;

  @Indexed
  private String username;

  @Getter
  private LocalDate dataNasc;

  @Getter
  @Indexed
  private String email;

  @Getter
  @Indexed
  private String cpf;

  private String password;

  @Getter
  @Indexed
  private String phone;

  @Getter
  private Role role;

  @Getter
  private String roll;

  public UserRedis() {}

  /**
   * userredis.
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
  public UserRedis(String id, String typePerson, String username, LocalDate dataNasc,
                   String password, String cpf, String phone,
                   String email, Role role) {
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
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  /**
   * authorities.
   *
   * @return author
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
            && Objects.equals(typePerson, user.typePerson)
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
    return Objects.hash(id, typePerson, username, dataNasc, cpf, email, password, phone, role);
  }
}
