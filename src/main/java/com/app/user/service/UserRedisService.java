package com.app.user.service;

import com.app.user.entity.User;
import com.app.user.entity.UserRedis;
import com.app.user.repository.UserRedisRepository;
import com.app.user.service.exception.UserEmailNotFoundException;
import com.app.user.service.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Service class for managing users in Redis.
 */
@Log4j2
@EnableScheduling
@Primary
@Service
public class UserRedisService implements UserDetailsService {

  private static final int EXPIRATION_TIME_MINUTES = 1;

  @Qualifier("userRedisRepository")
  private final UserRedisRepository userRedisRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  /**
   * Constructs the UserRedisService.
   *
   * @param userRedisRepository the repository for Redis cache
   * @param userService the main user service
   * @param passwordEncoder the password encoder
   * @param modelMapper the model mapper for conversions
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
   * Loads a user by their email.
   *
   * @param email the user's email
   * @return UserDetails for authentication
   * @throws UsernameNotFoundException if the email is not found
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRedisRepository.findByEmail(email)
        .or(() -> fetchAndCacheUserFromDatabase(email))
        .map(user -> new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.getAuthorities()))
        .orElseThrow(() -> new UserEmailNotFoundException(email));
  }

  private Optional<UserRedis> fetchAndCacheUserFromDatabase(String email) {
    return Optional.ofNullable(userService.getUserByUserEmail(email))
        .map(this::convertToUserRedis)
        .map(userRedis -> userRedisRepository.save(userRedis));
  }

  /**
   * Creates a new user with an encrypted password.
   *
   * @param userRedis the user to be created
   * @return the saved user
   * @throws IllegalArgumentException if the password is empty
   */
  public UserRedis createUser(UserRedis userRedis) {
    if (!StringUtils.hasText(userRedis.getPassword())) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    userRedis.setPassword(passwordEncoder.encode(userRedis.getPassword()));
    return userRedisRepository.save(userRedis);
  }

  /**
   * Retrieves a user by their email.
   *
   * @param email the user's email
   * @return the user found in Redis
   * @throws UserNotFoundException if the user is not found
   */
  public UserRedis getUserByUserEmail(String email) {
    return userRedisRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
  }

  /**
   * Updates user information based on CPF.
   *
   * @param cpf the user's CPF
   * @param userDetails the new user details
   * @return the updated user
   * @throws UserNotFoundException if the user is not found
   */
  public UserRedis updateUserByCpf(String cpf, UserRedis userDetails) {
    UserRedis user = userRedisRepository.findByCpf(cpf)
        .orElseThrow(() -> new UserNotFoundException("User with CPF " + cpf + " not found"));

    user.setUsername(userDetails.getUsername());
    user.setDataNasc(userDetails.getDataNasc());
    user.setPhone(userDetails.getPhone());
    return userRedisRepository.save(user);
  }

  /**
   * Retrieves all users from Redis.
   *
   * @return a list of all cached users
   */
  public List<UserRedis> getAllUsers() {
    return (List<UserRedis>) userRedisRepository.findAll();
  }

  /**
   * Converts a User entity to UserRedis.
   *
   * @param user the user entity
   * @return the UserRedis equivalent
   */
  public UserRedis convertToUserRedis(User user) {
    return modelMapper.map(user, UserRedis.class);
  }

  /**
   * Converts a UserRedis entity to User.
   *
   * @param userRedis the user cached entity
   * @return the User equivalent
   */
  public User convertToUser(UserRedis userRedis) {
    return modelMapper.map(userRedis, User.class);
  }

  /**
   * Merges cached users back to the main database periodically.
   */
  @Scheduled(fixedDelay = EXPIRATION_TIME_MINUTES * 60 * 1000)
  public void mergeUserList() {
    List<UserRedis> userRedisList = (List<UserRedis>) userRedisRepository.findAll();
    List<User> userList = userRedisList.stream()
        .map(this::convertToUser)
        .collect(Collectors.toList());
    if (!userList.isEmpty()) {
      userService.saveAll(userList);
      userRedisRepository.deleteAll(userRedisList);
      log.info("Merged and cleared Redis cache for {} users", userList.size());
    }
  }
}