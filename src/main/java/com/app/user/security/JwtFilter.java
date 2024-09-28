package com.app.user.security;

import com.app.user.service.AuthService;
import com.app.user.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * jwtfilter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final AuthService authService;

  /**
   * jwtfilter.
   *
   * @param tokenService the tokenservice
   * @param authService the authservice
   */
  @Autowired
  public JwtFilter(@Lazy TokenService tokenService, @Lazy AuthService authService) {
    this.tokenService = tokenService;
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    Optional<String> token = extractToken(request);
    if (token.isPresent()) {
      String subject = tokenService.validateToken(token.get());
      UserDetails userDetails = authService.getAuthenticatedUser(subject);
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null) {
      return Optional.empty();
    }
    return Optional.of(authHeader.replace("Bearer ", ""));
  }
}
