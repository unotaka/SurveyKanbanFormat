package com.example.userregistration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報を表すドメインエンティティクラスです。
 * データベースへの永続化対象となるユーザーの属性を保持します。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * ユーザーの一意なID。データベースの主キーに対応します。
     */
    private Long id;

    /**
     * ユーザー名。システム内で一意である必要があります。
     */
    private String username;

    /**
     * メールアドレス。システム内で一意である必要があります。
     */
    private String email;

    /**
     * 暗号化されたパスワード。
     */
    private String password;
}
