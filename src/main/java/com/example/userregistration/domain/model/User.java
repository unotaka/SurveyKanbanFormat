package com.example.userregistration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ユーザー情報を表すドメインエンティティです。
 * データベースに永続化されるユーザーの属性を保持します。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** ユーザーID */
    private Long id;

    /** ユーザー名 */
    private String username;

    /** ハッシュ化されたパスワード */
    private String hashedPassword;

    /** メールアドレス */
    private String email;

    /** 表示名 */
    private String displayName;

    /** 作成日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
