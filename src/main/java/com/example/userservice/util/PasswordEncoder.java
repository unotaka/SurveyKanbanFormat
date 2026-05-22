package com.example.userservice.util;

/**
 * パスワードのハッシュ化と検証を行うためのインターフェース。
 * 実装にはBCryptなどの強力なハッシュアルゴリズムが推奨される。
 */
public interface PasswordEncoder {

    /**
     * 生のパスワードをハッシュ化する。
     *
     * @param rawPassword 生のパスワード
     * @return ハッシュ化されたパスワード
     */
    String encode(String rawPassword);

    /**
     * 生のパスワードとハッシュ化されたパスワードが一致するか検証する。
     *
     * @param rawPassword       生のパスワード
     * @param encodedPassword ハッシュ化されたパスワード
     * @return 一致する場合はtrue、しない場合はfalse
     */
    boolean matches(String rawPassword, String encodedPassword);
}
