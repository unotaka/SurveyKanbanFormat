package com.example.userservice.exception;

/**
 * ユーザー登録処理中に発生する一般的な例外の基底クラス。
 */
public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message) {
        super(message);
    }

    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
