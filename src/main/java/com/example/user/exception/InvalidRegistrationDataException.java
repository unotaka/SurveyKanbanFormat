package com.example.user.exception;

/**
 * ユーザー登録データのバリデーションに失敗した場合にスローされる例外です。
 */
public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException(String message) {
        super(message);
    }
}
