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

/**
 * {@link UserService} インターフェースの具象クラスで、ユーザー登録に関するビジネスロジックを実装します。
 * 入力値のバリデーション、パスワードの暗号化、ユーザー情報のデータベース永続化を担当します。
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * UserServiceImplのコンストラクタ。
     * 必要な依存関係（UserRepositoryとPasswordEncoder）を注入します。
     *
     * @param userRepository ユーザーデータへのアクセスを提供するリポジトリ
     * @param passwordEncoder パスワードの暗号化と検証を行うエンコーダー
     * @throws NullPointerException userRepositoryまたはpasswordEncoderがnullの場合
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository, "userRepository must not be null.");
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder, "passwordEncoder must not be null.");
    }

    /**
     * 新しいユーザーを登録します。
     * 入力されたユーザー情報に対してバリデーションを行い、パスワードを暗号化した後、
     * データベースにユーザー情報を永続化します。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws ValidationException 入力値のバリデーションに失敗した場合（例: ユーザー名重複、メールアドレス重複、パスワードポリシー違反）
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws ApplicationException パスワード暗号化処理など、その他のシステムエラーが発生した場合
     * @throws NullPointerException requestがnullの場合
     */
    @Override
    public void registerUser(UserRegistrationRequest request) {
        Objects.requireNonNull(request, "UserRegistrationRequest must not be null.");

        // 1. バリデーションチェック
        validateUserRegistrationRequest(request);

        // 2. パスワードの暗号化
        String hashedPassword;
        try {
            hashedPassword = passwordEncoder.encode(request.password());
        } catch (ApplicationException e) {
            // PasswordEncoderがApplicationExceptionをスローする可能性があるので、そのまま再スロー
            throw new ApplicationException(ErrorCode.PASSWORD_ENCRYPTION_ERROR,
                    String.format("パスワードの暗号化に失敗しました。ユーザー名: %s", request.username()), e);
        } catch (Exception e) {
            // 予期せぬ暗号化エラーを補足し、ApplicationExceptionでラップ
            throw new ApplicationException(ErrorCode.PASSWORD_ENCRYPTION_ERROR,
                    String.format("パスワードの暗号化中に予期せぬエラーが発生しました。ユーザー名: %s", request.username()), e);
        }

        // 3. ユーザーオブジェクトの生成
        // Userクラスはドメインエンティティであり、Lombokまたは適切なコンストラクタを持つと仮定します。
        // ここでは、ユーザー名、メールアドレス、ハッシュ化されたパスワードを受け取るコンストラクタが存在すると仮定します。
        User newUser = new User(request.username(), request.email(), hashedPassword);

        // 4. データベースへの永続化
        try {
            userRepository.save(newUser);
        } catch (RepositoryException e) {
            // RepositoryExceptionはすでにErrorCodeを持っており、Systemエラーとして定義されているため、そのままスロー。
            throw e;
        } catch (Exception e) {
            // データベース操作中の予期せぬエラーを補足し、RepositoryExceptionでラップ。
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR,
                    String.format("ユーザー(%s)の登録中にデータベースエラーが発生しました。", request.username()), e);
        }
    }

    /**
     * ユーザー登録リクエストのバリデーションを行います。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws ValidationException バリデーションルールに違反した場合
     */
    private void validateUserRegistrationRequest(UserRegistrationRequest request) {
        // ユーザー名の重複チェック
        if (userRepository.existsByUsername(request.username())) {
            throw new ValidationException(ErrorCode.USERNAME_ALREADY_EXISTS,
                    String.format("ユーザー名 '%s' は既に登録されています。", request.username()));
        }

        // メールアドレスの重複チェック
        if (userRepository.existsByEmail(request.email())) {
            throw new ValidationException(ErrorCode.EMAIL_ALREADY_EXISTS,
                    String.format("メールアドレス '%s' は既に登録されています。", request.email()));
        }

        // パスワードの簡易ポリシーチェック (例: 空でないこと)
        // UserRegistrationRequestレコードのコンストラクタで既にnullチェックが行われていると仮定します。
        if (request.password().isBlank()) { // Java 11以降のString.isBlank()を使用
            throw new ValidationException(ErrorCode.PASSWORD_POLICY_VIOLATION,
                    "パスワードは空であってはなりません。");
        }
        // ここにさらに複雑なパスワードポリシー（文字数、文字種など）を追加できます。
    }
}
