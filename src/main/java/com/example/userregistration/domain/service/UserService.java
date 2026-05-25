package com.example.userregistration.domain.service;

import com.example.userregistration.application.dto.UserRegistrationRequest;
import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.UserRegistrationException;
import com.example.userregistration.common.exception.UserRegistrationValidationException;
import com.example.userregistration.domain.model.User;
import com.example.userregistration.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ユーザー登録に関するビジネスロジックを提供するサービスです。
 * 入力バリデーション、パスワード暗号化、ユーザー情報の永続化を担当します。
 */
@RequiredArgsConstructor
public class UserService {

    /**
     * メールアドレスの形式を検証するための正規表現パターン。
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    /**
     * ユーザー情報の永続化を担当するリポジトリインターフェース。
     */
    private final UserRepository userRepository;

    /**
     * パスワードの暗号化と検証を担当するエンコーダーインターフェース。
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 新しいユーザーを登録します。
     * 以下のステップを実行します:
     * 1. 入力リクエストのnullチェック。
     * 2. ユーザー名、メールアドレス、パスワードのバリデーション。
     *    - 各フィールドが空でないか。
     *    - メールアドレスの形式が正しいか。
     *    - パスワードと確認用パスワードが一致するか。
     *    - ユーザー名とメールアドレスが既に登録されていないか（重複チェック）。
     * 3. バリデーションに失敗した場合、{@link UserRegistrationValidationException} をスロー。
     * 4. パスワードを暗号化。
     * 5. ユーザーエンティティを作成し、リポジトリを通じてデータベースに永続化。
     * 6. データベース操作中にエラーが発生した場合、{@link UserRegistrationException} をスロー。
     *
     * @param request ユーザー登録のためのリクエストDTO。nullであってはなりません。
     * @throws UserRegistrationValidationException 入力バリデーションに失敗した場合。
     * @throws UserRegistrationException           データベース操作中にシステムエラーが発生した場合。
     * @throws NullPointerException                {@code request} がnullの場合。
     */
    public void registerUser(UserRegistrationRequest request) {
        Objects.requireNonNull(request, "User registration request must not be null.");

        List<String> errors = new ArrayList<>();

        // 1. 基本的な入力フィールドのバリデーション
        if (request.username() == null || request.username().trim().isEmpty()) {
            errors.add("ユーザー名が入力されていません。");
        }
        if (request.email() == null || request.email().trim().isEmpty()) {
            errors.add("メールアドレスが入力されていません。");
        } else if (!isValidEmail(request.email())) {
            errors.add("メールアドレスの形式が正しくありません。");
        }
        if (request.password() == null || request.password().trim().isEmpty()) {
            errors.add("パスワードが入力されていません。");
        }
        if (request.confirmPassword() == null || request.confirmPassword().trim().isEmpty()) {
            errors.add("確認用パスワードが入力されていません。");
        }

        // パスワードと確認用パスワードの一致チェック (両方が存在する場合のみ)
        if (request.password() != null && request.confirmPassword() != null &&
                !request.password().equals(request.confirmPassword())) {
            errors.add("パスワードと確認用パスワードが一致しません。");
        }

        // ここまでのエラーがあれば、重複チェックの前に例外をスロー
        if (!errors.isEmpty()) {
            throw new UserRegistrationValidationException(errors);
        }

        // 2. ユーザー名とメールアドレスの重複チェック
        try {
            if (userRepository.existsByUsername(request.username())) {
                errors.add("このユーザー名は既に登録されています。");
            }
            if (userRepository.existsByEmail(request.email())) {
                errors.add("このメールアドレスは既に登録されています。");
            }
        } catch (Exception e) {
            // UserRepositoryからのDBアクセス例外を捕捉し、システムエラーとして再スロー
            throw new UserRegistrationException(
                    ErrorCode.DATABASE_ERROR,
                    "ユーザー名またはメールアドレスの重複チェック中にデータベースエラーが発生しました。",
                    e
            );
        }

        // 重複チェックのエラーがあれば例外をスロー
        if (!errors.isEmpty()) {
            throw new UserRegistrationValidationException(errors);
        }

        // 3. パスワードの暗号化
        String encodedPassword;
        try {
            encodedPassword = passwordEncoder.encode(request.password());
        } catch (IllegalArgumentException e) {
            throw new UserRegistrationException(
                    ErrorCode.UNKNOWN_SYSTEM_ERROR,
                    "パスワードのエンコード中にエラーが発生しました。",
                    e
            );
        }

        // 4. Userエンティティの作成
        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(encodedPassword)
                .build();

        // 5. データベースへの永続化
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            // UserRepositoryからのDBアクセス例外を捕捉し、システムエラーとして再スロー
            throw new UserRegistrationException(
                    ErrorCode.DATABASE_ERROR,
                    "ユーザー情報のデータベース登録中にエラーが発生しました。",
                    e
            );
        }
    }

    /**
     * メールアドレスの形式が正しいかを簡易的に検証します。
     *
     * @param email 検証するメールアドレス文字列。
     * @return メールアドレスの形式が正しい場合は {@code true}、そうでない場合は {@code false}。
     */
    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
