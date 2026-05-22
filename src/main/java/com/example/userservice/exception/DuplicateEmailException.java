package com.example.userservice.exception;

/**
 * メールアドレスが既に登録されている場合にスローされる例外。
 */
public class DuplicateEmailException extends UserRegistrationException {
    public DuplicateEmailException(String email) {
        super("メールアドレス '" + email + "' は既に登録されています。");
    }
}
