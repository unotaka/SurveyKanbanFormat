package com.example.userregistration.common.exception;

/**
 * アプリケーション全体の基底例外クラス。
 * 業務エラーとシステムエラーの共通の親クラスとして機能します。
 */
public abstract class ApplicationException extends RuntimeException {

    /**
     * 指定された詳細メッセージを持つ新しいアプリケーション例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    protected ApplicationException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージと原因を持つ新しいアプリケーション例外を構築します。
     *
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    protected ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
