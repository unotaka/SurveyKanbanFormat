package com.example.userregistration.application.dto;

/**
 * ユーザー登録処理において、フロントエンドやコントローラー層から受け取るリクエストデータを表すDTOレコードです。
 *
 * @param username        登録するユーザー名。
 * @param email           登録するメールアドレス。
 * @param password        登録するパスワード。
 * @param confirmPassword パスワード確認用フィールド。{@code password} と一致する必要があります。
 */
public record UserRegistrationRequest(
        String username,
        String email,
        String password,
        String confirmPassword
) {
}
