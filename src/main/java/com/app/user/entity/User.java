package com.app.user.entity;

import com.app.user.security.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * user.
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;
  private String cpf;
  private String email;
  private String password;
  private String phone;
  private Role role;
  private String roll;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

  /**
   * user.
   */
  public User() {}

  /**
   * user.
   *
   * @param id the id
   * @param username the username
   * @param password the password
   * @param cpf the cpf
   * @param phone the phone
   * @param email the email
   * @param role the role
   */
  public User(Long id, String username, String password, String cpf,
              String phone, String email,
              Role role) {
    this.id = id;
    this.username = username;
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

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
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

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
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
    return List.of(new SimpleGrantedAuthority(roll));
  }
}