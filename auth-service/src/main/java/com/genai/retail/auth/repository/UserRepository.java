package com.genai.retail.auth.repository;

import com.genai.retail.auth.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
    extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}