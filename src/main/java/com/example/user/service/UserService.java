package com.example.user.service;

import com.example.user.domain.User;
import com.example.user.dto.UserRegistrationRequest;
import com.example.user.dto.UserRegistrationResponse;
import com.example.user.exception.EmailAlreadyExistsException;
import com.example.user.exception.InvalidRegistrationDataException;
import com.example.user.exception.UsernameAlreadyExistsException;
import com.example.user.repository.UserRepository;

import java.util.regex.Pattern;

/**
 * ユーザー登録処理を担うサービス層のクラスです。
 * 登録データの検証、ビジネスロジックの適用、永続化を行います。
 */
public class UserService {

    private final UserRepository userRepository;

    // パスワードのハッシュ化を模倣するための定数。実際は適切なライブラリを使用します。
    private static final String HASH_PREFIX = "{hashed}";

    // メールアドレスの正規表現パターン
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    /**
     * コンストラクタ。依存するUserRepositoryを注入します。
     *
     * @param userRepository ユーザーデータへのアクセスを提供するリポジトリ
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * ユーザー登録処理を実行します。
     * 入力データの検証、ユーザー名とメールアドレスの重複チェック、パスワードのハッシュ化、ユーザー情報の保存を行います。
     *
     * @param request ユーザー登録リクエストデータ
     * @return 登録されたユーザー情報を含むレスポンスデータ
     * @throws InvalidRegistrationDataException 登録データが無効な場合
     * @throws UsernameAlreadyExistsException ユーザー名が既に存在する場合
     * @throws EmailAlreadyExistsException メールアドレスが既に存在する場合
     */
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // 1. 入力データのバリデーション
        validateRegistrationRequest(request);

        // 2. ユーザー名の重複チェック
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new UsernameAlreadyExistsException("ユーザー名 '" + request.getUsername() + "' は既に使用されています。");
        });

        // 3. メールアドレスの重複チェック
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new EmailAlreadyExistsException("メールアドレス '" + request.getEmail() + "' は既に使用されています。");
        });

        // 4. パスワードのハッシュ化
        // 実際にはBCryptなどの強力なハッシュアルゴリズムを使用します
        String hashedPassword = hashPassword(request.getPassword());

        // 5. Userドメインエンティティの作成
        User newUser = new User(request.getUsername(), hashedPassword, request.getEmail());

        // 6. ユーザー情報を永続化
        User savedUser = userRepository.save(newUser);

        // 7. レスポンスDTOの作成と返却
        return new UserRegistrationResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "ユーザー登録が完了しました。"
        );
    }

    /**
     * ユーザー登録リクエストのフィールドを検証します。
     *
     * @param request ユーザー登録リクエストデータ
     * @throws InvalidRegistrationDataException バリデーションに失敗した場合
     */
    private void validateRegistrationRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new InvalidRegistrationDataException("登録リクエストがnullです。");
        }
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new InvalidRegistrationDataException("ユーザー名は必須です。");
        }
        if (request.getUsername().trim().length() < 3 || request.getUsername().trim().length() > 20) {
            throw new InvalidRegistrationDataException("ユーザー名は3文字以上20文字以下である必要があります。");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new InvalidRegistrationDataException("パスワードは必須です。");
        }
        if (request.getPassword().length() < 8) {
            throw new InvalidRegistrationDataException("パスワードは8文字以上である必要があります。");
        }
        // パスワードの複雑性チェックを追加することもできます（例: 大文字、小文字、数字、記号を含む）

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidRegistrationDataException("メールアドレスは必須です。");
        }
        if (!EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new InvalidRegistrationDataException("メールアドレスの形式が不正です。");
        }
    }

    /**
     * 実際にはセキュリティ要件に沿ったハッシュ化処理を実装します。
     * ここでは簡略化のためにプレフィックスを付与するだけです。
     *
     * @param password 平文のパスワード
     * @return ハッシュ化されたパスワード
     */
    private String hashPassword(String password) {
        // 本番環境ではSpring SecurityのBCryptPasswordEncoderなどを利用
        // 例: return new BCryptPasswordEncoder().encode(password);
        return HASH_PREFIX + password;
    }
}
