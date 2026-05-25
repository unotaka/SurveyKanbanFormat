package com.example.user.registration.dto;

/**
 * ユーザー登録リクエストのデータ転送オブジェクト (DTO) です。
 * ユーザー名、メールアドレス、パスワード、確認用パスワードを保持します。
 */
public record UserRegistrationRequest(
        /**
         * ユーザー名
         */
        String username,

        /**
         * メールアドレス
         */
        String email,

        /**
         * パスワード
         */
        String password,

        /**
         * 確認用パスワード
         */
        String confirmPassword
) {
}
