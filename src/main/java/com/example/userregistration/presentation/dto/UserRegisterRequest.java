package com.example.userregistration.presentation.dto;

/**
 * ユーザー登録リクエストのデータ転送オブジェクトです。
 * フロントエンドやコントローラー層からユーザー登録に必要な情報を受け取るために使用します。
 *
 * @param username    ユーザー名
 * @param password    パスワード
 * @param email       メールアドレス
 * @param displayName 表示名
 */
public record UserRegisterRequest(
        String username,
        String password,
        String email,
        String displayName
) {
}
