package com.example.userregistration.user.repository.impl;

import com.example.userregistration.user.domain.User;
import com.example.userregistration.user.repository.UserRepository;
import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository; // Springの@Repositoryアノテーションを想定

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link UserRepository} のインメモリ実装（モック/ダミー）です。
 * 実際にはデータベースへのアクセスを実装します。
 * シングルトンではなく、DIコンテナによって管理されることを想定しています。
 */
@Repository // Spring Frameworkのコンポーネントとして認識されるようにアノテーションを付与
@Slf4j // Lombokによるロガー
public class UserRepositoryImpl implements UserRepository {

    // スレッドセーフなインメモリデータベースを模倣
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    /**
     * 新しいユーザー情報をデータベースに保存します。
     *
     * @param user 保存するUserドメインオブジェクト。IDは通常nullで渡され、永続化時にDBによって生成されます。
     * @return 保存されたUserオブジェクト。通常、DBによって生成されたIDが設定されています。
     * @throws RepositoryException データベース操作中にエラーが発生した場合 (今回はモックなので擬似的に発生させる可能性あり)
     * @throws NullPointerException userがnullの場合
     */
    @Override
    public User save(User user) throws RepositoryException {
        Objects.requireNonNull(user, "User cannot be null for saving.");
        try {
            if (user.getId() == null) {
                Long newId = idCounter.incrementAndGet();
                user.setId(newId);
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                users.put(newId, user);
                log.info("User saved: id={}", newId);
            } else {
                // 既存ユーザーの更新
                if (!users.containsKey(user.getId())) {
                    log.warn("Attempted to update non-existent user with id: {}", user.getId());
                    // 実際のリポジトリではNotFoundExceptionなどをスローする可能性もある
                    throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "User not found for update.", new IllegalStateException("User ID not found"));
                }
                user.setUpdatedAt(LocalDateTime.now());
                users.put(user.getId(), user); // 既存のエントリを上書き
                log.info("User updated: id={}", user.getId());
            }
            // 防御的コピーを返す（実際のDB操作では新しいインスタンスが返されることが多い）
            return cloneUser(user);
        } catch (Exception e) {
            log.error("Failed to save user: {}", user.getUsername(), e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "Failed to save user.", e);
        }
    }

    /**
     * 指定されたユーザー名を持つユーザーが存在するかどうかを確認します。
     *
     * @param username 確認するユーザー名
     * @return ユーザーが存在する場合はtrue、存在しない場合はfalse
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException usernameがnullの場合
     */
    @Override
    public boolean existsByUsername(String username) throws RepositoryException {
        Objects.requireNonNull(username, "Username cannot be null for existence check.");
        try {
            return users.values().stream()
                    .anyMatch(u -> u.getUsername().equals(username));
        } catch (Exception e) {
            log.error("Failed to check existence of username: {}", username, e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "Failed to check username existence.", e);
        }
    }

    /**
     * 指定されたメールアドレスを持つユーザーが存在するかどうかを確認します。
     *
     * @param email 確認するメールアドレス
     * @return ユーザーが存在する場合はtrue、存在しない場合はfalse
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException emailがnullの場合
     */
    @Override
    public boolean existsByEmail(String email) throws RepositoryException {
        Objects.requireNonNull(email, "Email cannot be null for existence check.");
        try {
            return users.values().stream()
                    .anyMatch(u -> u.getEmail().equals(email));
        } catch (Exception e) {
            log.error("Failed to check existence of email: {}", email, e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "Failed to check email existence.", e);
        }
    }

    /**
     * 指定されたIDのユーザー情報を検索します。
     *
     * @param id 検索するユーザーID
     * @return ユーザーが存在する場合はそのUserオブジェクトを含むOptional、存在しない場合は空のOptional
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException idがnullの場合
     */
    @Override
    public Optional<User> findById(Long id) throws RepositoryException {
        Objects.requireNonNull(id, "ID cannot be null for finding user.");
        try {
            // 防御的コピーを返す
            return Optional.ofNullable(users.get(id))
                           .map(this::cloneUser);
        } catch (Exception e) {
            log.error("Failed to find user by id: {}", id, e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "Failed to find user by ID.", e);
        }
    }

    /**
     * 指定されたユーザー名のユーザー情報を検索します。
     *
     * @param username 検索するユーザー名
     * @return ユーザーが存在する場合はそのUserオブジェクトを含むOptional、存在しない場合は空のOptional
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws NullPointerException usernameがnullの場合
     */
    @Override
    public Optional<User> findByUsername(String username) throws RepositoryException {
        Objects.requireNonNull(username, "Username cannot be null for finding user.");
        try {
            // 防御的コピーを返す
            return users.values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .map(this::cloneUser);
        } catch (Exception e) {
            log.error("Failed to find user by username: {}", username, e);
            throw new RepositoryException(ErrorCode.DATABASE_OPERATION_ERROR, "Failed to find user by username.", e);
        }
    }

    /**
     * Userオブジェクトの防御的コピーを作成します。
     * 永続化層がService層にオブジェクトを返す際に、内部状態の意図しない変更を防ぐため。
     *
     * @param user コピー元となるUserオブジェクト
     * @return コピーされた新しいUserオブジェクト
     */
    private User cloneUser(User user) {
        if (user == null) {
            return null;
        }
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .hashedPassword(user.getHashedPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
