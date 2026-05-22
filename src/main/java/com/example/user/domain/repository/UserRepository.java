package com.example.user.domain.repository;

import com.example.user.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user); // Returns the saved user, possibly with generated ID
}
