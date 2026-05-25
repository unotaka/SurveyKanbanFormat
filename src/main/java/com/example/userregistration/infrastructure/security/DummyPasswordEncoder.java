package com.example.userregistration.infrastructure.security;

import com.example.userregistration.domain.service.PasswordEncoder;

import java.util.Objects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * {@link PasswordEncoder} インターフェースのダミー実装です。
 * 実際の本番環境では、より堅牢なパスワードハッシュアルゴリズム（例: BCrypt）を使用する実装に置き換えるべきです。
 * この実装では、SHA-256を用いてパスワードをハッシュ化し、Base64エンコードします。
 * matchesメソッドは、エンコードされたパスワードがそのまま一致するかどうかを検証します（ソルトは考慮しません）。
 */
public class DummyPasswordEncoder implements PasswordEncoder {

    /**
     * 生のパスワードをSHA-256でハッシュ化し、Base64でエンコードします。
     *
     * @param rawPassword 暗号化されていない生のパスワード。
     * @return SHA-256でハッシュ化され、Base64エンコードされたパスワード。
     * @throws IllegalArgumentException rawPasswordがnullまたは空の場合。
     * @throws RuntimeException パスワードエンコード中に予期せぬエラーが発生した場合。
     */
    @Override
    public String encode(String rawPassword) {
        Objects.requireNonNull(rawPassword, "Raw password must not be null.");
        if (rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Raw password must not be empty.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

    /**
     * 生のパスワードをエンコードし、指定されたエンコード済みパスワードと一致するかどうかを比較します。
     *
     * @param rawPassword     検証する生のパスワード。
     * @param encodedPassword 比較対象のエンコード済みパスワード。
     * @return 生のパスワードをエンコードした結果がエンコード済みパスワードと一致すれば {@code true}、そうでなければ {@code false}。
     * @throws IllegalArgumentException rawPasswordまたはencodedPasswordがnullまたは空の場合。
     * @throws RuntimeException パスワードエンコード中に予期せぬエラーが発生した場合。
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        Objects.requireNonNull(rawPassword, "Raw password must not be null.");
        Objects.requireNonNull(encodedPassword, "Encoded password must not be null.");
        if (rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Raw password must not be empty.");
        }
        if (encodedPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Encoded password must not be empty.");
        }

        return encode(rawPassword).equals(encodedPassword);
    }
}
