package com.example.user.service;

import com.example.user.domain.model.User;
import com.example.user.domain.repository.UserRepository;
import com.example.user.dto.UserRegistrationRequest;
import com.example.user.dto.UserRegistrationResponse;
import com.example.user.exception.InvalidInputException;
import com.example.user.exception.UserAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistrationService {

    private final UserRepository userRepository;

    // In a real application, you'd inject a password encoder like BCryptPasswordEncoder
    // For this example, we'll use a simple mock hash.
    // private final PasswordEncoder passwordEncoder; 

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // 1. Validate input
        validateRegistrationRequest(request);

        // 2. Check for existing user (username)
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("ユーザー名 '" + request.getUsername() + "' は既に存在します。");
        }

        // 3. Check for existing user (email)
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("メールアドレス '" + request.getEmail() + "' は既に存在します。");
        }

        // 4. Hash password (mock for now)
        String hashedPassword = hashPassword(request.getPassword());

        // 5. Create User entity
        User newUser = new User(
            request.getUsername(),
            hashedPassword,
            request.getEmail()
        );
        // ID, createdAt, updatedAt are set in the User constructor or by the repository

        // 6. Save user
        User savedUser = userRepository.save(newUser);

        // 7. Return success response
        return new UserRegistrationResponse(true, "ユーザー登録が成功しました。", savedUser.getId());
    }

    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (Objects.isNull(request)) {
            throw new InvalidInputException("リクエストボディがnullです。");
        }
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new InvalidInputException("ユーザー名は必須です。");
        }
        if (request.getUsername().length() < 4 || request.getUsername().length() > 20) {
            throw new InvalidInputException("ユーザー名は4文字以上20文字以下である必要があります。");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new InvalidInputException("パスワードは必須です。");
        }
        if (request.getPassword().length() < 8) {
            throw new InvalidInputException("パスワードは8文字以上である必要があります。");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("メールアドレスは必須です。");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new InvalidInputException("メールアドレスの形式が不正です。");
        }
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    // Mock password hashing for demonstration purposes
    // In a real application, use a robust hashing library like jBCrypt or Spring Security's BCryptPasswordEncoder
    private String hashPassword(String password) {
        // This is a placeholder. DO NOT use this in a production environment.
        // Always use a strong, one-way hashing algorithm with a salt.
        return "hashed_" + password + "_" + System.currentTimeMillis(); 
    }
}
