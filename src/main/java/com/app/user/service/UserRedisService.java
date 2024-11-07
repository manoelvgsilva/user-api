package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.entity.UserRedis;
import com.app.user.repository.UserRedisRepository;
import com.app.user.service.exception.UserEmailNotFoundException;
import com.app.user.service.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Service class for managing users.
 */
@Log4j2
@EnableScheduling
@Service
public class UserRedisService implements UserDetailsService {
  private final int min = 1000 * 60;
  private final long mins = min * 1;
  private final UserRedisRepository userRedisRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  /**
   * userredisservice.
   *
   * @param userRedisRepository the userredisrepository
   * @param userService the userservice
   * @param passwordEncoder the passwordencoder
   * @param modelMapper the modelmapper
   */
  @Autowired
  public UserRedisService(UserRedisRepository userRedisRepository, UserService userService,
                          PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
    this.userRedisRepository = userRedisRepository;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  }

  /**
   * loaduserbyusername.
   *
   * @param email the email
   * @return user
   * @throws UsernameNotFoundException the usernamenotfoundexception
   */
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserRedis user = userRedisRepository.findByEmail(email)
        .orElseGet(() -> {
          User mongoUser = userService.getUserByUserEmail(email);
          if (mongoUser != null) {
            UserRedis cachedUser = modelMapper.map(mongoUser, UserRedis.class);
            userRedisRepository.save(cachedUser);
            return cachedUser;
          }
          throw new UserEmailNotFoundException(email);
        });
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

  /**
   * mergeuserlist.
   */
  @Scheduled(fixedDelay = mins)
  public void mergeUserList() {
    List<UserRedis> listUserRedis = (List<UserRedis>) userRedisRepository.findAll();
    if (CollectionUtils.isEmpty(listUserRedis)){
      log.info("lista de usuarios nula ou invalida!");
    } else {
      List<User> listUser = new ArrayList<>();
    listUserRedis.stream().forEach(
        userRedis -> {
          User user = modelMapper.map(userRedis, User.class);
          listUser.add(user);
        }
    );
    userService.saveAll(listUser);
    userRedisRepository.deleteAll(listUserRedis);
    }
  }
}