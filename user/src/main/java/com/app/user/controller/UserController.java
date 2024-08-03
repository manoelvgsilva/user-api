package com.app.user.controller;

import com.app.user.controller.dto.UserCreationDto;
import com.app.user.controller.dto.UserDto;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@RequestBody UserCreationDto userCreationDto) {
    User save = userService.create(userCreationDto.toEntity());
    return UserDto.fromEntity(save);
  }
}