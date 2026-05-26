package com.example.userregistration.user.service;

import com.example.userregistration.user.dto.UserRegistrationRequest;
import com.example.userregistration.user.domain.User;
import com.example.userregistration.common.exception.ValidationException;
import com.example.userregistration.common.exception.ApplicationException;

/**
 * ユーザー関連のビジネスロジックを定義するサービスインターフェースです。
 * 特にユーザー登録処理を担当します。
 */
public interface UserService {

    /**
     * 新しいユーザーを登録します。
     * 入力データのバリデーション、ユーザー名・メールアドレスの重複チェック、
     * パスワードの暗号化、およびデータベースへの永続化を行います。
     *
     * @param request ユーザー登録情報を含むDTO
     * @return 登録されたユーザーのドメインオブジェクト
     * @throws IllegalArgumentException requestがnullの場合
     * @throws ValidationException ユーザー名、メールアドレスの重複、パスワードポリシー違反、またはその他の入力値不正が発生した場合
     * @throws ApplicationException システムエラー（例: パスワード暗号化失敗、データベース操作失敗）が発生した場合
     */
    User registerUser(UserRegistrationRequest request);
}
