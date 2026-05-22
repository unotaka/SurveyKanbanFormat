package com.example.user.dto;

import java.util.Objects;

/**
 * ユーザー登録処理の結果を返すためのDTOクラスです。
 */
public class UserRegistrationResponse {
    private String userId;
    private String username;
    private String email;
    private String message;

    public UserRegistrationResponse(String userId, String username, String email, String message) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationResponse that = (UserRegistrationResponse) o;
        return Objects.equals(userId, that.userId) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, message);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
               "userId='" + userId + '\'' +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", message='" + message + '\'' +
               '}';
    }
}
