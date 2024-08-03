package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    String hashPassword =
        new BCryptPasswordEncoder().encode(user.getPassword());
    return userRepository.save(user);
  }
}