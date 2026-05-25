package com.example.userregistration.application.usecase;

import com.example.userregistration.common.exception.BusinessException;
import com.example.userregistration.common.exception.SystemException;
import com.example.userregistration.presentation.dto.UserRegisterRequest;

/**
 * ユーザー登録に関するビジネスロジックを定義するインターフェースです。
 * プレゼンテーション層からのユーザー登録リクエストを受け付け、処理を行います。
 */
public interface UserService {

    /**
     * 新しいユーザーをシステムに登録します。
     * ユーザー登録リクエストDTOを受け取り、入力チェック、パスワードの暗号化、
     * データベースへの永続化を行います。
     *
     * @param request ユーザー登録リクエストDTO
     * @throws BusinessException 業務ロジック上のエラー（例：バリデーション違反、ユーザー名やメールアドレスの重複）が発生した場合
     * @throws SystemException   システムエラー（例：データベース接続失敗）が発生した場合
     */
    void registerUser(UserRegisterRequest request);
}
