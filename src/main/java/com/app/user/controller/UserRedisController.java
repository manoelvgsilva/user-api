package com.app.user.controller;

import com.app.user.controller.dto.UserRedisCreationDto;
import com.app.user.controller.dto.UserRedisDto;
import com.app.user.entity.UserRedis;
import com.app.user.service.UserRedisService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
 * usercontroller.
 */
@RestController
@RequestMapping("/usersredis")
public class UserRedisController {

  @Autowired
  @Lazy
  private UserRedisService userRedisService;

  @Autowired
  public void setUserRedisService(@Lazy UserRedisService userRedisService) {
    this.userRedisService = userRedisService;
  }

  /**
   * createuser.
   *
   * @param userRedisCreationDto the userrediscreationdto
   * @return user
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserRedisDto createUser(@RequestBody UserRedisCreationDto userRedisCreationDto) {
    UserRedis savedUser =
        userRedisService.createUser(userRedisCreationDto.toEntity());
    return UserRedisDto.fromEntity(savedUser);
  }

  @GetMapping("/{email}")
  public UserRedisDto getUserByUsername(@PathVariable String email) {
    UserRedis getUser = userRedisService.getUserByUserEmail(email);
    return UserRedisDto.fromEntity(getUser);
  }

  @PutMapping("/{cpf}")
  public UserRedisDto upUserByCpf(@PathVariable String cpf,
                              @RequestBody UserRedis userDetails) {
    UserRedis upUser = userRedisService.upUserByCpf(cpf, userDetails);
    return UserRedisDto.fromEntity(upUser);
  }

  /**
   * getallusers.
   *
   * @return allusers
   */
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<UserRedisDto> getAllUsers() {
    return userRedisService.getAllUser().stream()
        .map(UserRedisDto::fromEntity)
        .collect(Collectors.toList());
  }
}