package com.example.userregistration.user.dto;

import java.util.Objects;

/**
 * ユーザー登録リクエストのデータ転送オブジェクトです。
 * ユーザーが登録時に提供する情報（ユーザー名、メールアドレス、パスワード）を保持します。
 * Java 16以降のrecordとして定義され、不変です。
 */
public record UserRegistrationRequest(
    /** ユーザー名 */
    String username,
    /** メールアドレス */
    String email,
    /** パスワード */
    String password
) {
    /**
     * UserRegistrationRequestのコンストラクタ。
     * 各フィールドがnullでないことを検証します。
     *
     * @param username ユーザー名
     * @param email メールアドレス
     * @param password パスワード
     * @throws NullPointerException username, email, またはpasswordがnullの場合
     */
    public UserRegistrationRequest {
        Objects.requireNonNull(username, "Username must not be null.");
        Objects.requireNonNull(email, "Email must not be null.");
        Objects.requireNonNull(password, "Password must not be null.");
    }
}
