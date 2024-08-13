package com.app.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * tokenservice.
 */
@Service
public class TokenService {
  private final Algorithm algorithm;

  @Value("${api.security.token.secret:default-secret}")
  private String tokenSecret;

  /**
   * tokenservice.
   *
   * @param secret the secret
   */
  @Autowired
  public TokenService(@Value("${api.security.token.secret}") String secret) {
    this.algorithm = Algorithm.HMAC256(secret);
  }

  /**
   * generatedtoken.
   *
   * @param username the username
   * @return token
   */
  public String generateToken(String username) {
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(generateExpiration())
        .sign(algorithm);
  }

  /**
   * token.
   *
   * @param token the token
   * @return token
   */
  public String validateToken(String token) {
    return JWT.require(algorithm)
        .build()
        .verify(token)
        .getSubject();
  }

  /**
   * generate.
   *
   * @return expiration
   */
  private Instant generateExpiration() {
    return Instant.now()
        .plus(2, ChronoUnit.HOURS);
  }
}
