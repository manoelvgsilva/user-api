package com.app.user.controller;

import com.app.user.controller.dto.UserRedisCreationDto;
import com.app.user.controller.dto.UserRedisDto;
import com.app.user.entity.UserRedis;
import com.app.user.service.UserRedisService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing Redis-cached users.
 */
@RestController
@RequestMapping("/usersredis")
public class UserRedisController {

  private final UserRedisService userRedisService;

  @Autowired
  public UserRedisController(@Lazy UserRedisService userRedisService) {
    this.userRedisService = userRedisService;
  }

  /**
   * Creates a new user in Redis cache.
   *
   * @param userRedisCreationDto the data transfer object for user creation
   * @return the created user as a DTO
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @CachePut(value = "userRedis", key = "#userRedisCreationDto.email")
  public UserRedisDto createUser(@RequestBody UserRedisCreationDto userRedisCreationDto) {
    UserRedis savedUser = userRedisService.createUser(userRedisCreationDto.toEntity());
    return UserRedisDto.fromEntity(savedUser);
  }

  /**
   * Retrieves a user by email.
   *
   * @param email the email to search for
   * @return the user as a DTO
   */
  @GetMapping("/{email}")
  @Cacheable(value = "userRedis", key = "#email")
  public UserRedisDto getUserByEmail(@PathVariable String email) {
    UserRedis user = userRedisService.getUserByUserEmail(email);
    return UserRedisDto.fromEntity(user);
  }

  /**
   * Updates user information based on CPF.
   *
   * @param cpf         the CPF to search for
   * @param userDetails the new user details
   * @return the updated user as a DTO
   */
  @PutMapping("/{cpf}")
  @CachePut(value = "userRedis", key = "#userDetails.cpf")
  public UserRedisDto updateUserByCpf(
      @PathVariable String cpf,
      @RequestBody UserRedis userDetails) {
    UserRedis updatedUser = userRedisService.updateUserByCpf(cpf, userDetails);
    return UserRedisDto.fromEntity(updatedUser);
  }

  /**
   * Retrieves all users from Redis cache.
   *
   * @return a list of users as DTOs
   */
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserRedisDto> getAllUsers() {
    return userRedisService.getAllUsers().stream()
        .map(UserRedisDto::fromEntity)
        .collect(Collectors.toList());
  }
}