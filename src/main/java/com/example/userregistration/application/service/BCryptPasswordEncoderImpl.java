package com.example.userregistration.application.service;

import org.mindrot.jbcrypt.BCrypt;

/**
 * BCryptアルゴリズムを使用してパスワードのハッシュ化と検証を行うための実装クラスです。
 * 実際のプロジェクトでは、jbcryptライブラリをMaven/Gradleに追加する必要があります。
 * <p>
 * (Maven dependency: `&lt;dependency&gt;&lt;groupId&gt;org.mindrot&lt;/groupId&gt;&lt;artifactId&gt;jbcrypt&lt;/artifactId&gt;&lt;version&gt;0.4&lt;/version&gt;&lt;/dependency&gt;`)
 * </p>
 */
public class BCryptPasswordEncoderImpl implements PasswordEncoder {

    private static final int HASH_STRENGTH = 10; // BCryptのストレッチング回数（コストファクター）

    /**
     * 指定された生のパスワードをBCryptアルゴリズムでハッシュ化します。
     *
     * @param rawPassword ハッシュ化する生のパスワード
     * @return ハッシュ化されたパスワード
     */
    @Override
    public String encode(String rawPassword) {
        // Objects.requireNonNull(rawPassword, "rawPassword must not be null"); // Common rule states Objects.requireNonNull for public methods, but BCrypt.hashpw handles null gracefully by throwing IllegalArgumentException
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(HASH_STRENGTH));
    }

    /**
     * 指定された生のパスワードが、BCryptハッシュ化されたパスワードと一致するかどうかを検証します。
     *
     * @param rawPassword       検証する生のパスワード
     * @param encodedPassword ハッシュ化されたパスワード
     * @return 生のパスワードとハッシュ化されたパスワードが一致する場合は true、それ以外の場合は false
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        // Objects.requireNonNull(rawPassword, "rawPassword must not be null");
        // Objects.requireNonNull(encodedPassword, "encodedPassword must not be null");
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
