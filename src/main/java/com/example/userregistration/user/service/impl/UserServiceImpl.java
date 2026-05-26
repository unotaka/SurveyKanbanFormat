package com.example.userregistration.user.service.impl;

import com.example.userregistration.common.exception.ApplicationException;
import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.RepositoryException;
import com.example.userregistration.common.exception.ValidationException;
import com.example.userregistration.user.domain.User;
import com.example.userregistration.user.dto.UserRegistrationRequest;
import com.example.userregistration.user.repository.UserRepository;
import com.example.userregistration.user.service.PasswordEncoder;
import com.example.userregistration.user.service.UserService;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * {@link UserService} インターフェースの実装クラスです。
 * ユーザー登録に関するビジネスロジックを提供します。
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // パスワードポリシーの正規表現と最小要件の定義
    // 例: 最低8文字、大文字小文字数字記号をそれぞれ1つ以上含む。空白は不可。
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * UserServiceImplのコンストラクタ。
     * 必要なリポジトリとパスワードエンコーダーを依存性注入します。
     *
     * @param userRepository ユーザーデータへのアクセスを提供するリポジトリ
     * @param passwordEncoder パスワードの暗号化を行うエンコーダー
     * @throws NullPointerException userRepositoryまたはpasswordEncoderがnullの場合
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepository must not be null.");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "PasswordEncoder must not be null.");
    }

    /**
     * {@inheritDoc}
     * <p>このメソッドは以下の処理を行います。</p>
     * <ol>
     *     <li>入力DTOがnullでないことを検証します。</li>
     *     <li>入力データの基本的なバリデーション（空文字チェック、メールアドレス形式、パスワードポリシー）を行います。</li>
     *     <li>ユーザー名とメールアドレスの重複チェックを行います。</li>
     *     <li>パスワードを暗号化します。</li>
     *     <li>{@link User} ドメインオブジェクトを生成し、データベースに保存します。</li>
     * </ol>
     *
     * @param request ユーザー登録情報を含むDTO
     * @return 登録されたユーザーのドメインオブジェクト
     * @throws IllegalArgumentException requestがnullの場合
     * @throws ValidationException ユーザー名、メールアドレスの重複、パスワードポリシー違反、またはその他の入力値不正が発生した場合
     * @throws ApplicationException システムエラー（例: パスワード暗号化失敗、データベース操作失敗）が発生した場合
     */
    @Override
    public User registerUser(UserRegistrationRequest request) {
        Objects.requireNonNull(request, "UserRegistrationRequest must not be null.");

        // 1. 入力データのバリデーション
        validateRegistrationRequest(request);

        // 2. ユーザー名、メールアドレスの重複チェック
        checkDuplicateUser(request.username(), request.email());

        // 3. パスワードの暗号化
        String hashedPassword;
        try {
            hashedPassword = passwordEncoder.encode(request.password());
        } catch (ApplicationException e) {
            // PasswordEncoderが内部でApplicationExceptionをスローした場合、そのまま再スロー
            throw e;
        } catch (Exception e) {
            // 予期せぬパスワードエンコードエラーはシステムエラーとして扱う
            throw new ApplicationException(ErrorCode.PASSWORD_ENCRYPTION_ERROR,
                                           new StringBuilder("Failed to encode password for user: ").append(request.username()).toString(),
                                           e);
        }

        // 4. Userドメインオブジェクトの生成
        User newUser = new User(request.username(), request.email(), hashedPassword);

        // 5. データベースへの永続化
        try {
            return userRepository.save(newUser);
        } catch (RepositoryException e) {
            // RepositoryException は既に ApplicationException を継承しているので、そのまま再スロー
            throw e;
        } catch (Exception e) {
            // データベース操作中の予期せぬエラーはリポジトリ例外としてラップする
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR,
                                          new StringBuilder("Failed to save user ").append(newUser.getUsername()).append(" to database.").toString(),
                                          e);
        }
    }

    /**
     * ユーザー登録リクエストの各フィールドをバリデートします。
     *
     * @param request バリデート対象のユーザー登録リクエストDTO
     * @throws ValidationException 入力値が不正な場合
     */
    private void validateRegistrationRequest(UserRegistrationRequest request) {
        // 空文字チェック
        if (request.username().isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "ユーザー名は必須です。");
        }
        if (request.email().isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "メールアドレスは必須です。");
        }
        if (request.password().isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "パスワードは必須です。");
        }

        // メールアドレス形式チェック
        if (!EMAIL_PATTERN.matcher(request.email()).matches()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "メールアドレスの形式が不正です。");
        }

        // パスワードポリシーチェック
        if (!PASSWORD_PATTERN.matcher(request.password()).matches()) {
            throw new ValidationException(ErrorCode.PASSWORD_POLICY_VIOLATION, ErrorCode.PASSWORD_POLICY_VIOLATION.getMessage());
        }
    }

    /**
     * ユーザー名とメールアドレスの重複をチェックします。
     *
     * @param username チェック対象のユーザー名
     * @param email チェック対象のメールアドレス
     * @throws ValidationException ユーザー名またはメールアドレスが既に登録されている場合
     */
    private void checkDuplicateUser(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ValidationException(ErrorCode.USERNAME_ALREADY_EXISTS, ErrorCode.USERNAME_ALREADY_EXISTS.getMessage());
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new ValidationException(ErrorCode.EMAIL_ALREADY_EXISTS, ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }
}
