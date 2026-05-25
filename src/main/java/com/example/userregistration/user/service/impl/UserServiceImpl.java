package com.example.userregistration.user.service.impl;

import com.example.userregistration.user.domain.User;
import com.example.userregistration.user.dto.UserRegistrationRequest;
import com.example.userregistration.user.repository.UserRepository;
import com.example.userregistration.user.service.PasswordEncoder;
import com.example.userregistration.user.service.UserService;
import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.RepositoryException;
import com.example.userregistration.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * {@link UserService} インターフェースの具体的な実装クラスです。
 * ユーザー登録のビジネスロジックをここに記述します。
 */
@Service // Spring Frameworkのサービスコンポーネントとして認識されるようにアノテーションを付与
@Slf4j // Lombokによるロガー
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * UserServiceImplのコンストラクタです。
     * 必要な依存関係（UserRepositoryとPasswordEncoder）を注入します。
     *
     * @param userRepository ユーザーデータへのアクセスを担うリポジトリ
     * @param passwordEncoder パスワードの暗号化と検証を担うエンコーダ
     * @throws NullPointerException userRepositoryまたはpasswordEncoderがnullの場合
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository must not be null.");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "PasswordEncoder must not be null.");
    }

    /**
     * 新しいユーザーを登録します。
     *
     * <p>このメソッドは以下の処理を行います:</p>
     * <ol>
     *     <li>{@link UserRegistrationRequest}の基本的なバリデーション（DTOコンストラクタで実施済み）。</li>
     *     <li>ユーザー名とメールアドレスの重複チェック。</li>
     *     <li>パスワードの暗号化。</li>
     *     <li>暗号化されたパスワードを含むユーザー情報を永続化。</li>
     * </ol>
     *
     * @param request ユーザー登録に必要な情報を含むDTO
     * @return 登録されたユーザーのドメインオブジェクト（IDなどが付与されたもの）
     * @throws ValidationException ユーザー名またはメールアドレスが既に存在する場合、
     *                             または{@link UserRegistrationRequest}のコンストラクタで捕捉しきれない業務ルール違反があった場合
     * @throws RepositoryException データベース操作中にシステムエラーが発生した場合
     * @throws NullPointerException requestがnullの場合
     */
    @Override
    public User registerUser(UserRegistrationRequest request) throws ValidationException, RepositoryException {
        Objects.requireNonNull(request, "UserRegistrationRequest must not be null.");
        log.info("Attempting to register user with username: {}", request.username());

        // 1. 重複チェック
        validateUserUniqueness(request);

        // 2. パスワード暗号化
        String hashedPassword;
        try {
            hashedPassword = passwordEncoder.encode(request.password());
            log.debug("Password successfully encrypted for user: {}", request.username());
        } catch (RepositoryException e) {
            log.error("Failed to encrypt password for user: {}", request.username(), e);
            // passwordEncoderがRepositoryExceptionをスローする場合、それをそのままスローする
            throw e;
        } catch (Exception e) {
            log.error("An unexpected error occurred during password encryption for user: {}", request.username(), e);
            throw new RepositoryException(ErrorCode.PASSWORD_ENCRYPTION_ERROR, "パスワードの暗号化中に予期せぬエラーが発生しました。", e);
        }

        // 3. ユーザーオブジェクトの構築
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .hashedPassword(hashedPassword)
                // createdAtとupdatedAtはUserRepositoryのsaveメソッドで設定されることを想定
                // もしService層で設定が必要な場合はここでLocalDateTime.now()を設定
                .build();
        log.debug("User domain object created for registration.");

        // 4. ユーザー情報の永続化
        try {
            User savedUser = userRepository.save(newUser);
            log.info("User registered successfully with ID: {}", savedUser.getId());
            return savedUser;
        } catch (RepositoryException e) {
            log.error("Failed to save user {} to repository.", request.username(), e);
            throw e; // UserRepositoryからスローされたRepositoryExceptionをそのままスロー
        } catch (Exception e) {
            log.error("An unexpected error occurred during user persistence for user: {}", request.username(), e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "ユーザー情報の永続化中に予期せぬエラーが発生しました。", e);
        }
    }

    /**
     * ユーザー名とメールアドレスの重複をチェックします。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws ValidationException ユーザー名またはメールアドレスが既に存在する場合
     * @throws RepositoryException データベース操作中にシステムエラーが発生した場合
     */
    private void validateUserUniqueness(UserRegistrationRequest request) throws ValidationException, RepositoryException {
        // ユーザー名重複チェック
        if (userRepository.existsByUsername(request.username())) {
            log.warn("User registration failed: Username '{}' already exists.", request.username());
            throw new ValidationException(ErrorCode.USERNAME_ALREADY_EXISTS,
                    "指定されたユーザー名 (" + request.username() + ") は既に登録されています。");
        }

        // メールアドレス重複チェック
        if (userRepository.existsByEmail(request.email())) {
            log.warn("User registration failed: Email '{}' already exists.", request.email());
            throw new ValidationException(ErrorCode.EMAIL_ALREADY_EXISTS,
                    "指定されたメールアドレス (" + request.email() + ") は既に登録されています。");
        }
        log.debug("Username and email uniqueness check passed.");
    }
}
