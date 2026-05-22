package com.example.user.exception;

/**
 * 登録しようとしたユーザー名が既に存在する場合にスローされる例外です。
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
