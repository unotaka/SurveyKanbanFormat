package com.example.user.dto;

import java.util.Objects;

public class UserRegistrationResponse {
    private boolean success;
    private String message;
    private Long userId;

    public UserRegistrationResponse(boolean success, String message, Long userId) {
        this.success = success;
        this.message = message;
        this.userId = userId;
    }

    public UserRegistrationResponse(boolean success, String message) {
        this(success, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationResponse that = (UserRegistrationResponse) o;
        return success == that.success && Objects.equals(message, that.message) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, userId);
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
               "success=" + success +
               ", message='" + message + '\'' +
               ", userId=" + userId +
               '}';
    }
}
