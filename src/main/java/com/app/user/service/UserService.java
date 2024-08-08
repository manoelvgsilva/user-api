package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * userservice.
 */
@Service
public class UserService {
  private final UserRepository userRepository;

  /**
   * userservice.
   *
   * @param userRepository the userrepository
   */
  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * createuser.
   *
   * @param user the user
   * @return user
   */
  public User create(User user) {
    String hashPassword =
        new BCryptPasswordEncoder().encode(user.getPassword());
    return userRepository.save(user);
  }
}