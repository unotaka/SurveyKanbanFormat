package com.example.user.exception;

/**
 * 登録しようとしたメールアドレスが既に存在する場合にスローされる例外です。
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
