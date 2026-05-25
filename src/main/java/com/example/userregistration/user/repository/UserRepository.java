package com.example.userregistration.user.repository;

import com.example.userregistration.user.domain.User;
import com.example.userregistration.common.exception.RepositoryException;

import java.util.Optional;

/**
 * ユーザー情報の永続化および取得を行うためのリポジトリインターフェースです。
 * データベース操作を抽象化し、Service層からの依存性を疎結合にします。
 */
public interface UserRepository {

    /**
     * 新しいユーザー情報をデータベースに保存します。
     *
     * @param user 保存するUserドメインオブジェクト。IDは通常nullで渡され、永続化時にDBによって生成されます。
     * @return 保存されたUserオブジェクト。通常、DBによって生成されたIDが設定されています。
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException userがnullの場合
     */
    User save(User user) throws RepositoryException;

    /**
     * 指定されたユーザー名を持つユーザーが存在するかどうかを確認します。
     *
     * @param username 確認するユーザー名
     * @return ユーザーが存在する場合はtrue、存在しない場合はfalse
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException usernameがnullの場合
     */
    boolean existsByUsername(String username) throws RepositoryException;

    /**
     * 指定されたメールアドレスを持つユーザーが存在するかどうかを確認します。
     *
     * @param email 確認するメールアドレス
     * @return ユーザーが存在する場合はtrue、存在しない場合はfalse
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException emailがnullの場合
     */
    boolean existsByEmail(String email) throws RepositoryException;

    /**
     * 指定されたIDのユーザー情報を検索します。
     *
     * @param id 検索するユーザーID
     * @return ユーザーが存在する場合はそのUserオブジェクトを含むOptional、存在しない場合は空のOptional
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException idがnullの場合
     */
    Optional<User> findById(Long id) throws RepositoryException;

    /**
     * 指定されたユーザー名のユーザー情報を検索します。
     *
     * @param username 検索するユーザー名
     * @return ユーザーが存在する場合はそのUserオブジェクトを含むOptional、存在しない場合は空のOptional
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException usernameがnullの場合
     */
    Optional<User> findByUsername(String username) throws RepositoryException;
}
