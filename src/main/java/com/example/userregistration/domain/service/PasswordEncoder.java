package com.example.userregistration.domain.service;

/**
 * パスワードの暗号化と検証を行うインターフェースです。
 * セキュリティ層の実装に依存しない抽象化を提供します。
 */
public interface PasswordEncoder {

    /**
     * 生のパスワードを暗号化します。
     *
     * @param rawPassword 暗号化されていない生のパスワード。
     * @return 暗号化されたパスワード。
     * @throws IllegalArgumentException rawPasswordがnullまたは空の場合。
     */
    String encode(String rawPassword);

    /**
     * 生のパスワードが、指定された暗号化されたパスワードと一致するかどうかを検証します。
     *
     * @param rawPassword     検証する生のパスワード。
     * @param encodedPassword 暗号化されたパスワード。
     * @return 生のパスワードが暗号化されたパスワードと一致する場合は {@code true}、それ以外の場合は {@code false}。
     * @throws IllegalArgumentException rawPasswordまたはencodedPasswordがnullまたは空の場合。
     */
    boolean matches(String rawPassword, String encodedPassword);
}
