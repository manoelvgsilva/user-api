package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import com.app.user.service.exception.UserEmailNotFoundException;
import com.app.user.service.exception.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service class for managing users.
 */
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * Constructor to inject dependencies.
   *
   * @param userRepository the user repository
   * @param passwordEncoder the password encoder
   */
  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(email)
        .orElseThrow(() -> new UserEmailNotFoundException(email));
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities());
  }

  /**
   * Creates a new user with an encrypted password.
   *
   * @param user the user entity to be created
   * @return the saved user
   */
  public User createUser(User user) {
    if (!StringUtils.hasText(user.getPassword())) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    String hashPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashPassword);
    return userRepository.save(user);
  }

  /**
   * Retrieves a user by their email.
   *
   * @param email the email to search for
   * @return the user entity
   */
  public User getUserByUserEmail(String email) {
    return userRepository.findByUsername(email)
        .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
  }

  /**
   * Updates user information based on CPF.
   *
   * @param cpf the CPF to search for
   * @param userDetails the new user details
   * @return the updated user
   */
  public User upUserByCpf(String cpf, User userDetails) {
    User user = userRepository.findByCpf(cpf)
        .orElseThrow(() -> new UserNotFoundException("User with CPF " + cpf + " not found"));

    user.setUsername(userDetails.getUsername());
    user.setDataNasc(userDetails.getDataNasc());
    user.setPhone(userDetails.getPhone());
    return userRepository.save(user);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of users
   */
  public List<User> getAllUser() {
    return userRepository.findAll();
  }
}