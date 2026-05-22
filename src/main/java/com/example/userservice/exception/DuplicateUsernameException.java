package com.example.userservice.exception;

/**
 * ユーザー名が既に存在する場合にスローされる例外。
 */
public class DuplicateUsernameException extends UserRegistrationException {
    public DuplicateUsernameException(String username) {
        super("ユーザー名 '" + username + "' は既に存在します。");
    }
}
