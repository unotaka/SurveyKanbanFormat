package com.example.userservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ユーザー登録処理の結果を返すDTO。
 * 登録されたユーザーの概要情報を含む。
 */
public class UserRegistrationResponse {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    // コンストラクタ
    public UserRegistrationResponse(Long id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }

    // デフォルトコンストラクタ
    public UserRegistrationResponse() {
    }

    // Getter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setter (必要であれば)
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationResponse that = (UserRegistrationResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, createdAt);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", email='" + email + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}
