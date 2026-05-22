package com.example.userservice.repository;

import com.example.userservice.entity.User;

import java.util.Optional;

/**
 * ユーザー情報を永続化するためのリポジトリインターフェース。
 * データベース操作を抽象化する。
 */
public interface UserRepository {

    /**
     * ユーザーを保存する。新規作成または更新。
     *
     * @param user 保存するユーザーエンティティ
     * @return 保存されたユーザーエンティティ（IDが設定されている場合など）
     */
    User save(User user);

    /**
     * 指定されたユーザー名を持つユーザーを検索する。
     *
     * @param username ユーザー名
     * @return ユーザーが見つかった場合はOptional<User>、見つからない場合はOptional.empty()
     */
    Optional<User> findByUsername(String username);

    /**
     * 指定されたメールアドレスを持つユーザーを検索する。
     *
     * @param email メールアドレス
     * @return ユーザーが見つかった場合はOptional<User>、見つからない場合はOptional.empty()
     */
    Optional<User> findByEmail(String email);
}
