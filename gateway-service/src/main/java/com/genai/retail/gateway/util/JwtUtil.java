package com.genai.retail.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  private Key getSigningKey() {

    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public Claims extractClaims(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateToken(String token) {

    try {

      extractClaims(token);

      return true;

    } catch (Exception ex) {

      return false;
    }
  }
}