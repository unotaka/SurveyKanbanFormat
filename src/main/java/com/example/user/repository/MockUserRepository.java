package com.example.user.repository;

import com.example.user.domain.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserRepositoryインターフェースのモック実装です。
 * 実際のリポジトリ層がない場合に、メモリ上でユーザーデータを管理します。
 * 本番環境ではデータベースアクセスを伴う実装に置き換えられます。
 */
public class MockUserRepository implements UserRepository {

    // ユーザー名をキーとするマップ
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    // メールアドレスをキーとするマップ
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    // IDをキーとするマップ
    private final Map<String, User> usersById = new ConcurrentHashMap<>();


    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    @Override
    public User save(User user) {
        // 新規登録か更新かを判断
        if (usersById.containsKey(user.getId())) {
            // 既存ユーザーの更新
            usersByUsername.put(user.getUsername(), user);
            usersByEmail.put(user.getEmail(), user);
            usersById.put(user.getId(), user);
        } else {
            // 新規ユーザーの保存
            usersByUsername.put(user.getUsername(), user);
            usersByEmail.put(user.getEmail(), user);
            usersById.put(user.getId(), user);
        }
        return user;
    }
}
