package com.example.userservice.exception;

/**
 * パスワードが指定された要件を満たさない場合にスローされる例外。
 */
public class InvalidPasswordException extends UserRegistrationException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
