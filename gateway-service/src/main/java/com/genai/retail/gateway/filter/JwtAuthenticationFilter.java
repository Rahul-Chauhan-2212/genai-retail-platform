package com.genai.retail.gateway.filter;

import com.genai.retail.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String path = request.getRequestURI();

    if (path.contains("/auth")) {

      filterChain.doFilter(request, response);

      return;
    }

    String authHeader =
        request.getHeader(HttpHeaders.AUTHORIZATION);

    if (authHeader == null ||
        !authHeader.startsWith("Bearer ")) {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      return;
    }

    String token = authHeader.substring(7);

    if (!jwtUtil.validateToken(token)) {

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      return;
    }

    Claims claims = jwtUtil.extractClaims(token);

    request.setAttribute("claims", claims);

    filterChain.doFilter(request, response);
  }
}