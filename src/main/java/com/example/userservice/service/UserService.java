package com.example.userservice.service;

import com.example.userservice.dto.UserRegistrationRequest;
import com.example.userservice.dto.UserRegistrationResponse;
import com.example.userservice.entity.User;
import com.example.userservice.exception.*;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * ユーザー登録に関するビジネスロジックを提供するサービス層クラス。
 */
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // メールアドレスの簡易バリデーション用正規表現
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    /**
     * コンストラクタ。依存関係を注入する。
     *
     * @param userRepository ユーザーリポジトリ
     * @param passwordEncoder パスワードエンコーダー
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 新しいユーザーを登録する。
     * 入力値のバリデーション、ユーザー名・メールアドレスの重複チェック、
     * パスワードのハッシュ化、データベースへの保存を行う。
     *
     * @param request ユーザー登録リクエストDTO
     * @return 登録されたユーザー情報を含むレスポンスDTO
     * @throws InvalidUsernameException   ユーザー名が無効な場合
     * @throws InvalidEmailException      メールアドレスが無効な場合
     * @throws InvalidPasswordException   パスワードが無効な場合
     * @throws DuplicateUsernameException ユーザー名が既に存在する場合
     * @throws DuplicateEmailException    メールアドレスが既に存在する場合
     * @throws UserRegistrationException  その他の登録失敗
     */
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // 1. 入力値のバリデーション
        validateRegistrationRequest(request);

        String username = request.getUsername();
        String email = request.getEmail();
        String rawPassword = request.getPassword();

        // 2. ユーザー名とメールアドレスの重複チェック
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new DuplicateUsernameException(username);
        });

        userRepository.findByEmail(email).ifPresent(u -> {
            throw new DuplicateEmailException(email);
        });

        // 3. パスワードのハッシュ化
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 4. Userエンティティの作成
        LocalDateTime now = LocalDateTime.now();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setHashedPassword(hashedPassword);
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);

        // 5. データベースに保存
        User savedUser = userRepository.save(newUser);

        // 6. 登録結果のDTOを返す
        return new UserRegistrationResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }

    /**
     * ユーザー登録リクエストの入力値をバリデーションする。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws InvalidUsernameException   ユーザー名が無効な場合
     * @throws InvalidEmailException      メールアドレスが無効な場合
     * @throws InvalidPasswordException   パスワードが無効な場合
     */
    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("リクエストはnullにできません。");
        }

        // ユーザー名バリデーション
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new InvalidUsernameException("ユーザー名は必須です。");
        }
        if (request.getUsername().length() < 4 || request.getUsername().length() > 20) {
            throw new InvalidUsernameException("ユーザー名は4文字以上20文字以下である必要があります。");
        }
        // 他のユーザー名要件（例: 半角英数字のみなど）を追加可能

        // メールアドレスバリデーション
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidEmailException("メールアドレスは必須です。");
        }
        if (!EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new InvalidEmailException("メールアドレスの形式が不正です。");
        }

        // パスワードバリデーション
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new InvalidPasswordException("パスワードは必須です。");
        }
        if (request.getPassword().length() < 8) {
            throw new InvalidPasswordException("パスワードは8文字以上である必要があります。");
        }
        // より厳格なパスワードポリシー（大文字、小文字、数字、記号を含むなど）を追加可能
        // if (!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$").matcher(request.getPassword()).matches()) {
        //     throw new InvalidPasswordException("パスワードは8文字以上で、大文字、小文字、数字、記号をそれぞれ1つ以上含んでください。");
        // }
    }
}
