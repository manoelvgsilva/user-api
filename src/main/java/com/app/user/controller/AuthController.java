package com.app.user.controller;

import com.app.user.controller.dto.AuthDto;
import com.app.user.controller.dto.TokenDto;
import com.app.user.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * authcontroller.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  /**
   * authcontroller.
   *
   * @param authenticationManager the autenticationmanager
   */
  @Autowired
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * login.
   *
   * @param authDto the authdto
   * @return login
   */
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.CREATED)
  public TokenDto login(@RequestBody AuthDto authDto) {
    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(authDto.username(),
            authDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    String token = tokenService.generateToken(auth.getName());
    return new TokenDto(token);
  }
}