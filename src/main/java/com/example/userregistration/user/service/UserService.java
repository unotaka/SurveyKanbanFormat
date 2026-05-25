package com.example.userregistration.user.service;

import com.example.userregistration.user.dto.UserRegistrationRequest;
import com.example.userregistration.user.domain.User;
import com.example.userregistration.common.exception.ValidationException;
import com.example.userregistration.common.exception.RepositoryException;

/**
 * ユーザー登録に関連するビジネスロジックを定義するサービスインターフェースです。
 * このインターフェースを実装することで、具体的な処理ロジックと利用側を疎結合にします。
 */
public interface UserService {

    /**
     * 新しいユーザーを登録します。
     *
     * <p>このメソッドは以下の処理を行います:</p>
     * <ol>
     *     <li>{@link UserRegistrationRequest}の基本的なバリデーション（DTOコンストラクタで実施済み）。</li>
     *     <li>ユーザー名とメールアドレスの重複チェック。</li>
     *     <li>パスワードの暗号化。</li>
     *     <li>暗号化されたパスワードを含むユーザー情報を永続化。</li>
     * </ol>
     *
     * @param request ユーザー登録に必要な情報を含むDTO
     * @return 登録されたユーザーのドメインオブジェクト（IDなどが付与されたもの）
     * @throws ValidationException ユーザー名またはメールアドレスが既に存在する場合、
     *                             または{@link UserRegistrationRequest}のコンストラクタで捕捉しきれない業務ルール違反があった場合
     * @throws RepositoryException データベース操作中にシステムエラーが発生した場合
     * @throws NullPointerException requestがnullの場合
     */
    User registerUser(UserRegistrationRequest request) throws ValidationException, RepositoryException;
}
