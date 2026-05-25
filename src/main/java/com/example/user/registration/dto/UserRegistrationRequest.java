package com.example.user.registration.dto;

import java.util.Objects;

/**
 * ユーザー登録リクエストのデータ転送オブジェクト（DTO）。
 * 新規ユーザー登録時にクライアントから送信される情報を保持します。
 * <p>
 * この{@code record}は、以下の情報を含みます：
 * <ul>
 *   <li>{@code username}: ユーザー名。システム内で一意である必要があります。</li>
 *   <li>{@code email}: メールアドレス。システム内で一意である必要があります。</li>
 *   <li>{@code password}: パスワード。セキュリティのため、サービス層でハッシュ化されるべき平文のパスワード。</li>
 *   <li>{@code firstName}: ユーザーの姓。</li>
 *   <li>{@code lastName}: ユーザーの名。</li>
 * </ul>
 * </p>
 *
 * @param username ユーザー名
 * @param email メールアドレス
 * @param password パスワード
 * @param firstName ユーザーの姓
 * @param lastName ユーザーの名
 */
public record UserRegistrationRequest(
    String username,
    String email,
    String password,
    String firstName,
    String lastName
) {
    /**
     * UserRegistrationRequestの新しいインスタンスを生成します。
     * すべてのコンポーネント（フィールド）はnullであってはなりません。
     *
     * @param username ユーザー名。nullであってはなりません。
     * @param email メールアドレス。nullであってはなりません。
     * @param password パスワード。nullであってはなりません。
     * @param firstName ユーザーの姓。nullであってはなりません。
     * @param lastName ユーザーの名。nullであってはなりません。
     * @throws NullPointerException いずれかのコンポーネントがnullの場合
     */
    public UserRegistrationRequest {
        Objects.requireNonNull(username, "ユーザー名はnullであってはなりません。");
        Objects.requireNonNull(email, "メールアドレスはnullであってはなりません。");
        Objects.requireNonNull(password, "パスワードはnullであってはなりません。");
        Objects.requireNonNull(firstName, "姓はnullであってはなりません。");
        Objects.requireNonNull(lastName, "名はnullであってはなりません。");
    }
}
