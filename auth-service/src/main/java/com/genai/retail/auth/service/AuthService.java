package com.genai.retail.auth.service;

import com.genai.retail.auth.dto.AuthResponse;
import com.genai.retail.auth.dto.LoginRequest;
import com.genai.retail.auth.dto.RegisterRequest;
import com.genai.retail.auth.entity.User;
import com.genai.retail.auth.repository.UserRepository;
import com.genai.retail.auth.util.JwtUtil;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtUtil jwtUtil;

  public AuthResponse register(RegisterRequest request) {

    userRepository.findByEmail(request.getEmail())
        .ifPresent(user -> {

          throw new RuntimeException(
              "User already exists"
          );
        });

    User user = User.builder()
        .email(request.getEmail())
        .password(
            passwordEncoder.encode(
                request.getPassword()
            )
        )
        .role("MARKETER")
        .active(true)
        .createdAt(LocalDateTime.now())
        .build();

    userRepository.save(user);

    String token = jwtUtil.generateToken(
        user.getEmail(),
        user.getRole()
    );

    return AuthResponse.builder()
        .token(token)
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }

  public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(
            request.getEmail()
        )
        .orElseThrow(() ->
            new RuntimeException("Invalid credentials")
        );

    boolean validPassword =
        passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
        );

    if (!validPassword) {

      throw new RuntimeException(
          "Invalid credentials"
      );
    }

    String token = jwtUtil.generateToken(
        user.getEmail(),
        user.getRole()
    );

    return AuthResponse.builder()
        .token(token)
        .email(user.getEmail())
        .role(user.getRole())
        .build();
  }
}