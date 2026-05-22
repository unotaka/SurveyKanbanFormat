package com.example.userservice.exception;

/**
 * メールアドレスが指定された形式を満たさない場合にスローされる例外。
 */
public class InvalidEmailException extends UserRegistrationException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
