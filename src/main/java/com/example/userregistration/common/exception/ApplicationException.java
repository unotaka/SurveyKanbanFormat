package com.example.userregistration.common.exception;

/**
 * アプリケーション固有の例外の基底抽象クラスです。
 * 業務エラーとシステムエラーの共通の属性（エラーコードなど）を定義します。
 */
public abstract class ApplicationException extends RuntimeException {

    /**
     * この例外に関連付けられたエラーコード。
     */
    private final ErrorCode errorCode;

    /**
     * 指定されたエラーコードと詳細メッセージを使用して、新しいApplicationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられたエラーコード。
     * @param message   この例外の詳細メッセージ。
     */
    public ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 指定されたエラーコード、詳細メッセージ、および原因となった例外を使用して、
     * 新しいApplicationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられたエラーコード。
     * @param message   この例外の詳細メッセージ。
     * @param cause     この例外の根本原因。
     */
    public ApplicationException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * この例外に関連付けられたエラーコードを返します。
     *
     * @return この例外に関連付けられた {@link ErrorCode}。
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
