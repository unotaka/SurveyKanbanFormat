package com.example.userregistration.user.service;

import com.example.userregistration.common.exception.RepositoryException; // 具体的な暗号化エラーをラップするための例外

import java.util.Objects;

/**
 * パスワードのハッシュ化および検証を行うためのインターフェースです。
 * 実際の暗号化アルゴリズムの実装を抽象化します。
 */
public interface PasswordEncoder {

    /**
     * 生のパスワードをハッシュ化します。
     *
     * @param rawPassword ハッシュ化する生のパスワード
     * @return ハッシュ化されたパスワード文字列
     * @throws RepositoryException パスワードのハッシュ化中にシステムエラーが発生した場合
     * @throws NullPointerException rawPasswordがnullの場合
     */
    String encode(String rawPassword) throws RepositoryException;

    /**
     * ハッシュ化されたパスワードと生のパスワードが一致するかどうかを検証します。
     *
     * @param rawPassword 検証する生のパスワード
     * @param encodedPassword 比較対象のハッシュ化されたパスワード
     * @return パスワードが一致すればtrue、そうでなければfalse
     * @throws RepositoryException パスワードの検証中にシステムエラーが発生した場合
     * @throws NullPointerException rawPasswordまたはencodedPasswordがnullの場合
     */
    boolean matches(String rawPassword, String encodedPassword) throws RepositoryException;

    /**
     * 指定された文字列がnullでないことを保証し、nullの場合は{@link NullPointerException}をスローします。
     * Javadocの引数チェック要件を満たすために、インターフェースのデフォルトメソッドとして提供。
     *
     * @param arg チェックする文字列
     * @param paramName 引数の名前 (エラーメッセージ用)
     * @return チェックされた文字列
     * @throws NullPointerException 引数がnullの場合
     */
    default <T> T requireNonNull(T arg, String paramName) {
        return Objects.requireNonNull(arg, paramName + " must not be null.");
    }
}
