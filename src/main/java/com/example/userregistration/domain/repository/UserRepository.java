package com.example.userregistration.domain.repository;

import com.example.userregistration.domain.model.User;

/**
 * ユーザーエンティティの永続化操作を定義するリポジトリインターフェースです。
 * ドメイン層がデータソース層に依存する際の抽象化を提供します。
 */
public interface UserRepository {

    /**
     * 新しいユーザー情報をデータベースに保存します。
     *
     * @param user 保存するユーザーエンティティ。
     * @return 保存されたユーザーエンティティ（IDが割り当てられている場合など）。
     * @throws com.example.userregistration.common.exception.UserRegistrationException データベース操作中にエラーが発生した場合。
     */
    User save(User user);

    /**
     * 指定されたユーザー名を持つユーザーが既に存在するかどうかを確認します。
     *
     * @param username 確認するユーザー名。
     * @return ユーザー名が既に存在する場合は {@code true}、それ以外の場合は {@code false}。
     * @throws com.example.userregistration.common.exception.UserRegistrationException データベース操作中にエラーが発生した場合。
     */
    boolean existsByUsername(String username);

    /**
     * 指定されたメールアドレスを持つユーザーが既に存在するかどうかを確認します。
     *
     * @param email 確認するメールアドレス。
     * @return メールアドレスが既に存在する場合は {@code true}、それ以外の場合は {@code false}。
     * @throws com.example.userregistration.common.exception.UserRegistrationException データベース操作中にエラーが発生した場合。
     */
    boolean existsByEmail(String email);
}
