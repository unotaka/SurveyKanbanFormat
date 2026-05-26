package com.example.userregistration.user.repository;

import com.example.userregistration.user.domain.User;
import com.example.userregistration.common.exception.RepositoryException;
import java.util.Optional;

/**
 * ユーザーデータへの永続化アクセスを定義するリポジトリインターフェースです。
 * データベース操作に関する抽象化を提供します。
 */
public interface UserRepository {

    /**
     * 指定されたユーザー名を持つユーザーを検索します。
     *
     * @param username 検索するユーザー名
     * @return ユーザーが見つかった場合は{@link Optional}にラップされたUserオブジェクト、それ以外の場合は空のOptional
     * @throws IllegalArgumentException usernameがnullまたは空の場合
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     */
    Optional<User> findByUsername(String username);

    /**
     * 指定されたメールアドレスを持つユーザーを検索します。
     *
     * @param email 検索するメールアドレス
     * @return ユーザーが見つかった場合は{@link Optional}にラップされたUserオブジェクト、それ以外の場合は空のOptional
     * @throws IllegalArgumentException emailがnullまたは空の場合
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     */
    Optional<User> findByEmail(String email);

    /**
     * 新しいユーザーをデータベースに保存します。
     *
     * @param user 保存するユーザーエンティティ
     * @return 保存されたUserオブジェクト (通常はIDが割り当てられた状態)
     * @throws IllegalArgumentException userがnullの場合
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     */
    User save(User user);
}
