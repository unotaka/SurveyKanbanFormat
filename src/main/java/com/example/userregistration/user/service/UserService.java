package com.example.userregistration.user.service;

import com.example.userregistration.common.exception.ApplicationException;
import com.example.userregistration.common.exception.RepositoryException;
import com.example.userregistration.common.exception.ValidationException;
import com.example.userregistration.user.dto.UserRegistrationRequest;

/**
 * ユーザー関連のビジネスロジックを定義するサービスインターフェースです。
 */
public interface UserService {

    /**
     * 新しいユーザーを登録します。
     * 入力されたユーザー情報に対してバリデーションを行い、パスワードを暗号化した後、
     * データベースにユーザー情報を永続化します。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws ValidationException 入力値のバリデーションに失敗した場合（例: ユーザー名重複、メールアドレス重複、パスワードポリシー違反）
     * @throws RepositoryException データベース操作中にエラーが発生した場合
     * @throws ApplicationException パスワード暗号化処理など、その他のシステムエラーが発生した場合
     * @throws NullPointerException requestがnullの場合
     */
    void registerUser(UserRegistrationRequest request);
}
