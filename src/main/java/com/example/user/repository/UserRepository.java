package com.example.user.repository;

import com.example.user.domain.User;

import java.util.Optional;

/**
 * ユーザーデータへのアクセスを抽象化するリポジトリインターフェースです。
 * データベース操作を模倣します。
 */
public interface UserRepository {

    /**
     * 指定されたユーザー名を持つユーザーを検索します。
     *
     * @param username ユーザー名
     * @return ユーザーが見つかった場合はそのユーザーを保持するOptional、見つからない場合は空のOptional
     */
    Optional<User> findByUsername(String username);

    /**
     * 指定されたメールアドレスを持つユーザーを検索します。
     *
     * @param email メールアドレス
     * @return ユーザーが見つかった場合はそのユーザーを保持するOptional、見つからない場合は空のOptional
     */
    Optional<User> findByEmail(String email);

    /**
     * ユーザー情報を保存または更新します。
     *
     * @param user 保存または更新するユーザーオブジェクト
     * @return 保存または更新されたユーザーオブジェクト
     */
    User save(User user);
}
