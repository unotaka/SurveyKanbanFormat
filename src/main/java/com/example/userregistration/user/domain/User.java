package com.example.userregistration.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ユーザー情報を表すドメインエンティティです。
 * データベースに永続化されるユーザーの具体的なデータを保持します。
 * Lombokを使用し、ボイラープレートコードを削減しています。
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor // デフォルトコンストラクタ（Lombokで生成）
@AllArgsConstructor // 全フィールドを引数とするコンストラクタ（Lombokで生成）
public class User {

    /**
     * ユーザーの一意な識別子（主キー）。
     */
    private Long id;

    /**
     * ユーザー名。
     */
    private String username;

    /**
     * メールアドレス。
     */
    private String email;

    /**
     * ハッシュ化されたパスワード。
     */
    private String hashedPassword;

    /**
     * アカウント作成日時。
     */
    private LocalDateTime createdAt;

    /**
     * アカウント最終更新日時。
     */
    private LocalDateTime updatedAt;

    /**
     * Userオブジェクトを構築します。
     * このコンストラクタは主にLombokの{@code @Builder}アノテーションが使用される場合に便利ですが、
     * 直接呼び出すことも可能です。
     *
     * @param id ユーザーID
     * @param username ユーザー名
     * @param email メールアドレス
     * @param hashedPassword ハッシュ化されたパスワード
     * @param createdAt 作成日時
     * @param updatedAt 更新日時
     */
    // JavadocはLombokで生成されるコンストラクタに対しては不要ですが、
    // 明示的な記述を求める共通ルールに従い、@AllArgsConstructorに対応する形で記述しています。
    // @NoArgsConstructorについても同様にJavacDocを省略しています。
    // 実運用ではLombok利用を前提とするため、これらの手書きJavadocは通常は不要です。
    // CHECKSTYLE:OFF
    // PMD:OFF
    // (Lombokの生成するメソッドに対する警告を抑制するコメント)
    // CHECKSTYLE:ON
    // PMD:ON

    /**
     * {@inheritDoc}
     *
     * @param o 比較対象のオブジェクト
     * @return オブジェクトが等しい場合はtrue、そうでない場合はfalse
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    /**
     * {@inheritDoc}
     *
     * @return このオブジェクトのハッシュコード
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
