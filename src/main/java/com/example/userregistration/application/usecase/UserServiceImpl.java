package com.example.userregistration.application.usecase;

import com.example.userregistration.application.service.PasswordEncoder;
import com.example.userregistration.application.service.UserValidator;
import com.example.userregistration.common.exception.BusinessException;
import com.example.userregistration.common.exception.SystemException;
import com.example.userregistration.domain.model.User;
import com.example.userregistration.domain.repository.UserRepository;
import com.example.userregistration.presentation.dto.UserRegisterRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link UserService} インターフェースの実装クラスです。
 * ユーザー登録に関するビジネスロジックを実行します。
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    /**
     * UserServiceImplのコンストラクタです。
     * 依存関係を注入するために使用されます。
     *
     * @param userRepository  ユーザーデータ永続化層へのインターフェース
     * @param passwordEncoder パスワードのハッシュ化と検証を行うサービス
     * @param userValidator   ユーザー登録リクエストのバリデーションを行うユーティリティ
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository must not be null");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null");
        this.userValidator = Objects.requireNonNull(userValidator, "userValidator must not be null");
    }

    /**
     * 新しいユーザーをシステムに登録します。
     * ユーザー登録リクエストDTOを受け取り、入力チェック、パスワードの暗号化、
     * データベースへの永続化を行います。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws BusinessException 業務ロジック上のエラー（例：バリデーション違反、ユーザー名やメールアドレスの重複）が発生した場合
     * @throws SystemException   システムエラー（例：データベース接続失敗）が発生した場合
     */
    @Override
    public void registerUser(UserRegisterRequest request) {
        Objects.requireNonNull(request, "UserRegisterRequest must not be null.");

        // 1. DTOのバリデーション
        List<String> validationErrors = userValidator.validate(request);
        if (!validationErrors.isEmpty()) {
            throw new BusinessException(validationErrors);
        }

        List<String> businessErrors = new ArrayList<>();

        // 2. ユーザー名とメールアドレスの重複チェック
        if (userRepository.existsByUsername(request.username())) {
            businessErrors.add("指定されたユーザー名は既に存在します。");
        }
        if (userRepository.existsByEmail(request.email())) {
            businessErrors.add("指定されたメールアドレスは既に登録されています。");
        }

        if (!businessErrors.isEmpty()) {
            throw new BusinessException(businessErrors);
        }

        // 3. パスワードの暗号化
        String hashedPassword = passwordEncoder.encode(request.password());

        // 4. Userエンティティの生成
        LocalDateTime now = LocalDateTime.now();
        User newUser = User.builder()
                .username(request.username())
                .hashedPassword(hashedPassword)
                .email(request.email())
                .displayName(request.displayName())
                .createdAt(now)
                .updatedAt(now)
                .build();

        // 5. データベースへ永続化
        try {
            userRepository.save(newUser);
        } catch (SystemException e) {
            // リポジトリ層で発生したシステムエラーを上位に伝える
            throw new SystemException("ユーザー登録中にデータベースエラーが発生しました。", e);
        }
    }
}
