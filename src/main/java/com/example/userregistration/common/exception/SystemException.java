package com.example.userregistration.common.exception;

/**
 * システムレベルで発生するエラー（例：DB接続失敗、予期せぬIOエラーなど）を表す例外クラス。
 * 通常、この例外はリカバリ不可能であり、システムの運用者に通知されるべきです。
 */
public class SystemException extends ApplicationException {

    /**
     * 指定された詳細メッセージを持つ新しいシステム例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * 指定された詳細メッセージと原因を持つ新しいシステム例外を構築します。
     *
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
