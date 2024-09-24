package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import com.app.user.service.exception.UserEmailNotFoundException;
import com.app.user.service.exception.UserNotFoundException;
import java.util.List;
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

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserEmailNotFoundException(email));
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities());
  }

  /**
   * createuser.
   *
   * @param user the user
   * @return user
   */
  public User createUser(User user) {
    String hashPassword = new BCryptPasswordEncoder().encode(user.getPassword());
    user.setPassword(hashPassword);
    userRepository.save(user);
    return user;
  }

  /**
   * getuser.
   *
   * @param email the email
   * @return user
   */
  public User getUserByUserEmail(String email) {
    Optional<User> user = userRepository.findByEmail(email);
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
  public User upUserByCpf(String cpf, User userDetails) {
    Optional<User> optionalUser = userRepository.findByCpf(cpf);
    if (optionalUser.isEmpty()) {
      throw new UserNotFoundException();
    }
    User user = optionalUser.get();
    user.setUsername(userDetails.getUsername());
    user.setDataNasc(userDetails.getDataNasc());
    user.setPhone(userDetails.getPhone());
    userRepository.save(user);
    return user;
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }
}