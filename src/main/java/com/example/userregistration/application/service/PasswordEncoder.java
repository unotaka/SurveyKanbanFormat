package com.example.userregistration.application.service;

/**
 * パスワードのハッシュ化と検証を行うためのインターフェースです。
 * セキュリティ上の理由から、パスワードは平文で保存せず、ハッシュ化して保存する必要があります。
 */
public interface PasswordEncoder {

    /**
     * 指定された生のパスワードをハッシュ化します。
     *
     * @param rawPassword ハッシュ化する生のパスワード
     * @return ハッシュ化されたパスワード
     */
    String encode(String rawPassword);

    /**
     * 指定された生のパスワードが、ハッシュ化されたパスワードと一致するかどうかを検証します。
     *
     * @param rawPassword       検証する生のパスワード
     * @param encodedPassword ハッシュ化されたパスワード
     * @return 生のパスワードとハッシュ化されたパスワードが一致する場合は true、それ以外の場合は false
     */
    boolean matches(String rawPassword, String encodedPassword);
}
