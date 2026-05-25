package com.example.userregistration.infrastructure.repository;

import com.example.userregistration.common.exception.SystemException;
import com.example.userregistration.domain.model.User;
import com.example.userregistration.domain.repository.UserRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * インメモリでユーザーデータを管理する {@link UserRepository} の実装クラスです。
 * 永続化層のモックとして機能し、実際のデータベース接続は行いません。
 * 開発やテスト目的で利用されます。
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<String, User> usersByUsername = new ConcurrentHashMap<>();
    private final Map<String, User> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * 指定されたユーザー名を持つユーザーが存在するかどうかをチェックします。
     *
     * @param username チェックするユーザー名
     * @return ユーザー名が存在する場合は true、それ以外の場合は false
     */
    @Override
    public boolean existsByUsername(String username) {
        return usersByUsername.containsKey(username);
    }

    /**
     * 指定されたメールアドレスを持つユーザーが存在するかどうかをチェックします。
     *
     * @param email チェックするメールアドレス
     * @return メールアドレスが存在する場合は true、それ以外の場合は false
     */
    @Override
    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(email);
    }

    /**
     * 新しいユーザー情報をインメモリデータベースに保存します。
     * IDが設定されていない場合は自動生成し、タイムスタンプを設定します。
     *
     * @param user 保存するユーザーエンティティ
     * @return 保存されたユーザーエンティティ（IDなどが設定されたもの）
     * @throws SystemException 予期せぬエラーが発生した場合 (この実装ではほぼ発生しないが、インターフェース契約に準拠)
     */
    @Override
    public User save(User user) {
        try {
            // IDが設定されていない場合、新しいIDを生成
            if (user.getId() == null) {
                user.setId(idCounter.incrementAndGet());
            }

            // タイムスタンプの設定はService層で設定済みを想定するが、
            // もし設定されていなければここで設定 (ここではService層で設定済みを前提とする)
            // user.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now());
            // user.setUpdatedAt(LocalDateTime.now());

            users.put(user.getId(), user);
            usersByUsername.put(user.getUsername(), user);
            usersByEmail.put(user.getEmail(), user);
            return user;
        } catch (Exception e) {
            // 実際のDB接続で発生する可能性のあるエラーを模倣
            throw new SystemException("インメモリリポジトリでの保存中にエラーが発生しました。", e);
        }
    }

    /**
     * 指定されたIDのユーザーを検索します。
     *
     * @param id ユーザーID
     * @return ユーザーが見つかった場合はそのUserオブジェクトを含むOptional、見つからなかった場合は空のOptional
     */
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
}
