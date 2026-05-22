package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserRegisterRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Integer age;
}
package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserRegisterResponseDto {
    private String userId;
    private String userName;
    private String message;
}
package com.example.userservice.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
package com.example.userservice.entity;

import lombok.Data;

@Data
public class User {
    private String id; // 例: UUIDなどの内部ID
    private String userId; // メールアドレス (ユニークキー)
    private String passwordHash;
    private String userName;
    private Integer age;
}
package com.example.userservice.repository;

import com.example.userservice.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserId(String userId);
    User save(User user);
}
package com.example.userservice.service;

import com.example.userservice.dto.UserRegisterRequestDto;
import com.example.userservice.dto.UserRegisterResponseDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.DuplicateUserException;
import com.example.userservice.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;
import java.util.UUID; // 仮のID生成用

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegisterResponseDto registerUser(UserRegisterRequestDto request) {
        // 1. 入力値チェック
        validateUserInput(request);

        // 2. 重複チェック
        if (userRepository.findByUserId(request.getUserId()).isPresent()) {
            throw new DuplicateUserException("ユーザーID " + request.getUserId() + " は既に存在します。");
        }

        // 3. パスワードハッシュ化
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // 4. ユーザー情報登録
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString()); // DBでのID生成をシミュレート
        newUser.setUserId(request.getUserId());
        newUser.setPasswordHash(hashedPassword);
        newUser.setUserName(request.getUserName());
        newUser.setAge(request.getAge());

        User savedUser = userRepository.save(newUser);

        // 5. レスポンス生成
        UserRegisterResponseDto response = new UserRegisterResponseDto();
        response.setUserId(savedUser.getUserId());
        response.setUserName(savedUser.getUserName());
        response.setMessage("ユーザー登録が完了しました。");

        return response;
    }

    private void validateUserInput(UserRegisterRequestDto request) {
        // userId (メールアドレス形式)
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (request.getUserId() == null || !Pattern.matches(emailRegex, request.getUserId())) {
            throw new IllegalArgumentException("不正なユーザーID（メールアドレス）形式です。");
        }

        // password (最低8文字、英数字記号をそれぞれ1種類以上)
        // 少なくとも1つの小文字、1つの大文字、1つの数字、1つの特殊文字、そして8文字以上
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
        if (request.getPassword() == null || !Pattern.matches(passwordRegex, request.getPassword())) {
            throw new IllegalArgumentException("パスワードは最低8文字、英数字記号をそれぞれ1種類以上含む必要があります。");
        }

        // userName (空でないこと)
        if (request.getUserName() == null || request.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("ユーザー名は必須です。");
        }

        // age (0以上150以下の整数)
        if (request.getAge() == null || request.getAge() < 0 || request.getAge() > 150) {
            throw new IllegalArgumentException("年齢は0歳以上150歳以下の整数で入力してください。");
        }
    }
}
