package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.entity.UserRedis;
import com.app.user.repository.UserRedisRepository;
import com.app.user.service.exception.UserEmailNotFoundException;
import com.app.user.service.exception.UserNotFoundException;
import java.util.List;
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
public class UserRedisService implements UserDetailsService {
  private final UserRedisRepository userRedisRepository;
  private final PasswordEncoder passwordEncoder;

  public UserRedisService(UserRedisRepository userRedisRepository,
                          PasswordEncoder passwordEncoder) {
    this.userRedisRepository = userRedisRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserRedis user = userRedisRepository.findByEmail(email)
        .orElseThrow(() -> new UserEmailNotFoundException(email));
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities());
  }

  /**
   * Creates a new user with an encrypted password.
   *
   * @param userRedis the user entity to be created
   * @return the saved user
   */
  public UserRedis createUser(UserRedis userRedis) {
    if (!StringUtils.hasText(userRedis.getPassword())) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    String hashPassword = passwordEncoder.encode(userRedis.getPassword());
    userRedis.setPassword(hashPassword);
    return userRedisRepository.save(userRedis);
  }

  /**
   * Retrieves a user by their email.
   *
   * @param email the email to search for
   * @return the user entity
   */
  public UserRedis getUserByUserEmail(String email) {
    return userRedisRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
  }

  /**
   * Updates user information based on CPF.
   *
   * @param cpf the CPF to search for
   * @param userDetails the new user details
   * @return the updated user
   */
  public UserRedis upUserByCpf(String cpf, UserRedis userDetails) {
    UserRedis user = userRedisRepository.findByCpf(cpf)
        .orElseThrow(() -> new UserNotFoundException("User with CPF " + cpf + " not found"));

    user.setUsername(userDetails.getUsername());
    user.setDataNasc(userDetails.getDataNasc());
    user.setPhone(userDetails.getPhone());
    return userRedisRepository.save(user);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of users
   */
  public List<UserRedis> getAllUser() {
    return (List<UserRedis>) userRedisRepository.findAll();
  }
}