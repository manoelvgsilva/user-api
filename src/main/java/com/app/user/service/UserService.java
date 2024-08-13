package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import com.app.user.service.exception.UserNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * userservice.
 */
@Service
public class UserService implements UserDetailsService {
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
  public User createUser(User user) {
    String hashPassword =
        new BCryptPasswordEncoder().encode(user.getPassword());
    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  /**
   * getuser.
   *
   * @param username the username
   * @return user
   */
  public User getUserByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }
    return user.get();
  }

  /**
   * upuser.
   *
   * @param cpf the cpf
   * @return user
   */
  public User upUserByCpf(String cpf) {
    Optional<User> user = userRepository.findByCpf(cpf);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }
    User upUser = user.get();
    upUser.setUsername(upUser.getUsername());
    upUser.setPhone(upUser.getPhone());
    return upUser;
  }
}