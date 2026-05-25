package com.example.userregistration.user.dto;

import java.util.regex.Pattern;
import java.util.Objects;
import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.ValidationException;

/**
 * ユーザー登録処理の入力データを表すDTO（Data Transfer Object）です。
 * 不変オブジェクトとして設計されており、recordで定義されます。
 * 入力値の基本的なバリデーションルールが定義されています。
 *
 * @param username ユーザー名
 * @param email メールアドレス
 * @param password パスワード
 */
public record UserRegistrationRequest(
        String username,
        String email,
        String password
) {
    // メールアドレスの正規表現 (RFC 5322に厳密に従うものではなく、一般的な形式をカバー)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    // パスワードの複雑性要件: 最低8文字、大文字・小文字・数字・特殊文字をそれぞれ1つ以上含む
    private static final Pattern PASSWORD_POLICY_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$");

    /**
     * UserRegistrationRequestのコンストラクタ。
     * 入力値の基本的なバリデーションを行います。
     *
     * @param username ユーザー名
     * @param email メールアドレス
     * @param password パスワード
     * @throws ValidationException 入力値がバリデーションルールに違反する場合
     */
    public UserRegistrationRequest {
        Objects.requireNonNull(username, "ユーザー名がnullです。");
        Objects.requireNonNull(email, "メールアドレスがnullです。");
        Objects.requireNonNull(password, "パスワードがnullです。");

        if (username.isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "ユーザー名は必須です。");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "ユーザー名は3文字以上50文字以下で入力してください。");
        }

        if (email.isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "メールアドレスは必須です。");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "メールアドレスの形式が不正です。");
        }
        if (email.length() > 255) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "メールアドレスは255文字以下で入力してください。");
        }

        if (password.isBlank()) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "パスワードは必須です。");
        }
        if (!PASSWORD_POLICY_PATTERN.matcher(password).matches()) {
            throw new ValidationException(ErrorCode.PASSWORD_POLICY_VIOLATION,
                    "パスワードは8文字以上で、大文字、小文字、数字、特殊文字をそれぞれ1つ以上含んでください。");
        }
        if (password.length() > 255) {
            throw new ValidationException(ErrorCode.VALIDATION_ERROR, "パスワードは255文字以下で入力してください。");
        }
    }
}
