package com.example.userservice.exception;

/**
 * ユーザー名が指定された要件を満たさない場合にスローされる例外。
 */
public class InvalidUsernameException extends UserRegistrationException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
