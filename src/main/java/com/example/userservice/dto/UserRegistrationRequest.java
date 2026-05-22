package com.example.userservice.dto;

import java.util.Objects;

/**
 * ユーザー登録処理の入力データを受け取るDTO。
 */
public class UserRegistrationRequest {
    private String username;
    private String email;
    private String password;

    // コンストラクタ
    public UserRegistrationRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // デフォルトコンストラクタ（Jacksonなどのデシリアライズ用）
    public UserRegistrationRequest() {
    }

    // Getter
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter (必要であれば、通常は不変性を保つためにSetterは提供しないことが多い)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationRequest that = (UserRegistrationRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
               "username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", password='[PROTECTED]'" + // パスワードはログに出さない
               '}';
    }
}
