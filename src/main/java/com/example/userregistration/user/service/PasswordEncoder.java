package com.example.userregistration.user.service;

import com.example.userregistration.common.exception.ApplicationException;

/**
 * パスワードの暗号化（ハッシュ化）と検証を行うサービスインターフェースです。
 */
public interface PasswordEncoder {

    /**
     * 生のパスワードを暗号化（ハッシュ化）します。
     *
     * @param rawPassword 暗号化する生のパスワード
     * @return 暗号化されたパスワードの文字列
     * @throws IllegalArgumentException rawPasswordがnullまたは空の場合
     * @throws ApplicationException パスワード暗号化処理中にエラーが発生した場合
     */
    String encode(String rawPassword);

    /**
     * 生のパスワードと暗号化されたパスワードが一致するかを検証します。
     *
     * @param rawPassword 生のパスワード
     * @param encodedPassword 暗号化されたパスワード
     * @return パスワードが一致する場合はtrue、それ以外の場合はfalse
     * @throws IllegalArgumentException rawPasswordまたはencodedPasswordがnullまたは空の場合
     */
    boolean matches(String rawPassword, String encodedPassword);
}
