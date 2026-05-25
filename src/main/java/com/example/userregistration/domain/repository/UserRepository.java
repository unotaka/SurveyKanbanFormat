package com.example.userregistration.domain.repository;

import com.example.userregistration.domain.model.User;

import java.util.Optional;

/**
 * ユーザー永続化層へのインターフェースです。
 * ユーザーデータの保存、検索、存在チェックなどの操作を定義します。
 */
public interface UserRepository {

    /**
     * 指定されたユーザー名を持つユーザーが存在するかどうかをチェックします。
     *
     * @param username チェックするユーザー名
     * @return ユーザー名が存在する場合は true、それ以外の場合は false
     * @throws com.example.userregistration.common.exception.SystemException データアクセスエラーが発生した場合
     */
    boolean existsByUsername(String username);

    /**
     * 指定されたメールアドレスを持つユーザーが存在するかどうかをチェックします。
     *
     * @param email チェックするメールアドレス
     * @return メールアドレスが存在する場合は true、それ以外の場合は false
     * @throws com.example.userregistration.common.exception.SystemException データアクセスエラーが発生した場合
     */
    boolean existsByEmail(String email);

    /**
     * 新しいユーザー情報をデータベースに保存します。
     *
     * @param user 保存するユーザーエンティティ
     * @return 保存されたユーザーエンティティ（IDなどが設定されたもの）
     * @throws com.example.userregistration.common.exception.SystemException データアクセスエラーが発生した場合
     */
    User save(User user);

    /**
     * 指定されたIDのユーザーを検索します。
     *
     * @param id ユーザーID
     * @return ユーザーが見つかった場合はそのUserオブジェクトを含むOptional、見つからなかった場合は空のOptional
     * @throws com.example.userregistration.common.exception.SystemException データアクセスエラーが発生した場合
     */
    Optional<User> findById(Long id);
}
