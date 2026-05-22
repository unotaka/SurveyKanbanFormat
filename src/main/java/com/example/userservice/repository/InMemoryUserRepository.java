package com.example.userservice.repository;

import com.example.userservice.entity.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * UserRepositoryインターフェースのインメモリ実装。
 * テストやシンプルなアプリケーションでの利用を想定。
 * 本番環境ではSpring Data JPAなどを使用した永続化層の実装に置き換える。
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> usernameIndex = new ConcurrentHashMap<>();
    private final Map<String, User> emailIndex = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(0L);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // 新規ユーザー
            user.setId(currentId.incrementAndGet());
        }
        users.put(user.getId(), user);
        usernameIndex.put(user.getUsername(), user);
        emailIndex.put(user.getEmail(), user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usernameIndex.get(username));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(emailIndex.get(email));
    }
}
