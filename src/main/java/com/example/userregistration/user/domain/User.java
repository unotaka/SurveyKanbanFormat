package com.example.userregistration.user.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * ユーザーエンティティ。
 * ユーザーのID、ユーザー名、メールアドレス、暗号化されたパスワードを保持します。
 * ドメインロジックや状態変更可能性を考慮し、標準のJavaクラスとして実装されています。
 */
public class User {

    private final String userId;
    private String username;
    private String email;
    private String hashedPassword;

    /**
     * ユーザーエンティティの新しいインスタンスを生成します。
     * IDはUUIDで自動生成されます。
     *
     * @param username ユーザー名
     * @param email メールアドレス
     * @param hashedPassword 暗号化されたパスワード
     * @throws IllegalArgumentException username, email, またはhashedPasswordがnullまたは空の場合
     */
    public User(String username, String email, String hashedPassword) {
        this(UUID.randomUUID().toString(), username, email, hashedPassword);
    }

    /**
     * ユーザーエンティティの新しいインスタンスを生成します。
     *
     * @param userId ユーザーの一意なID
     * @param username ユーザー名
     * @param email メールアドレス
     * @param hashedPassword 暗号化されたパスワード
     * @throws IllegalArgumentException userId, username, email, またはhashedPasswordがnullまたは空の場合
     */
    public User(String userId, String username, String email, String hashedPassword) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID must not be null or blank.");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be null or blank.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank.");
        }
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Hashed password must not be null or blank.");
        }

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    /**
     * ユーザーIDを取得します。
     *
     * @return ユーザーID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ユーザー名を取得します。
     *
     * @return ユーザー名
     */
    public String getUsername() {
        return username;
    }

    /**
     * ユーザー名を設定します。
     *
     * @param username 新しいユーザー名
     * @throws IllegalArgumentException usernameがnullまたは空の場合
     */
    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be null or blank.");
        }
        this.username = username;
    }

    /**
     * メールアドレスを取得します。
     *
     * @return メールアドレス
     */
    public String getEmail() {
        return email;
    }

    /**
     * メールアドレスを設定します。
     *
     * @param email 新しいメールアドレス
     * @throws IllegalArgumentException emailがnullまたは空の場合
     */
    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank.");
        }
        this.email = email;
    }

    /**
     * 暗号化されたパスワードを取得します。
     *
     * @return 暗号化されたパスワード
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * 暗号化されたパスワードを設定します。
     *
     * @param hashedPassword 新しい暗号化されたパスワード
     * @throws IllegalArgumentException hashedPasswordがnullまたは空の場合
     */
    public void setHashedPassword(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Hashed password must not be null or blank.");
        }
        this.hashedPassword = hashedPassword;
    }

    /**
     * このオブジェクトと指定されたオブジェクトが等しいかどうかを比較します。
     * ユーザーIDに基づいて比較を行います。
     *
     * @param o 比較対象のオブジェクト
     * @return 指定されたオブジェクトがこのオブジェクトと等しい場合は{@code true}、そうでない場合は{@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    /**
     * このオブジェクトのハッシュコード値を返します。
     * ユーザーIDに基づいてハッシュコードを生成します。
     *
     * @return このオブジェクトのハッシュコード値
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    /**
     * このオブジェクトの文字列表現を返します。
     *
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return new StringBuilder("User{")
                .append("userId='").append(userId).append('\'')
                .append(", username='").append(username).append('\'')
                .append(", email='").append(email).append('\'')
                .append('}')
                .toString();
    }
}
