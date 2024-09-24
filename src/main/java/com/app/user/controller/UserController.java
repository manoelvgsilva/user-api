package com.app.user.controller;

import com.app.user.controller.dto.UserCreationDto;
import com.app.user.controller.dto.UserDto;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  /**
   * usercontroller.
   *
   * @param userService the userservice
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * createuser.
   *
   * @param userCreationDto the usercreationdto
   * @return user
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto createUser(@RequestBody UserCreationDto userCreationDto) {
    User saveUser = userService.createUser(userCreationDto.toEntity());
    return UserDto.fromEntity(saveUser);
  }

  @GetMapping("/{email}")
  public UserDto getUserByUsername(@PathVariable String email) {
    User getUser = userService.getUserByUserEmail(email);
    return UserDto.fromEntity(getUser);
  }

  @PutMapping("/{cpf}")
  public UserDto upUserByCpf(@PathVariable String cpf, @RequestBody User userDetails) {
    User upUser = userService.upUserByCpf(cpf, userDetails);
    return UserDto.fromEntity(upUser);
  }

  /**
   * getallusers.
   *
   * @return allusers
   */
  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN') or #user.email matches '^[a-zA-Z0-9._-]+@gmail\\.com$'")
  public List<UserDto> getAllUsers() {
    return userService.getAllUser().stream()
        .map(UserDto::fromEntity)
        .collect(Collectors.toList());
  }
}