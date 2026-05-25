package com.example.userregistration.common.exception;

import java.util.Objects;

/**
 * アプリケーション固有の例外の基底クラスです。
 * 業務エラーとシステムエラーの両方をラップし、{@link ErrorCode} を保持します。
 */
public class ApplicationException extends RuntimeException {

    /**
     * この例外に関連付けられたエラーコードです。
     */
    private final ErrorCode errorCode;

    /**
     * 指定されたエラーコードとメッセージを使用して、新しいApplicationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられるエラーコード
     * @param message 例外の詳細メッセージ
     * @throws NullPointerException errorCodeがnullの場合
     */
    public ApplicationException(ErrorCode errorCode, String message) {
        super(Objects.requireNonNull(message, "Error message must not be null."));
        this.errorCode = Objects.requireNonNull(errorCode, "ErrorCode must not be null.");
    }

    /**
     * 指定されたエラーコード、メッセージ、および原因となる例外を使用して、
     * 新しいApplicationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられるエラーコード
     * @param message 例外の詳細メッセージ
     * @param cause この例外の原因となる例外 (nullが許容される)
     * @throws NullPointerException errorCodeがnullの場合
     */
    public ApplicationException(ErrorCode errorCode, String message, Throwable cause) {
        super(Objects.requireNonNull(message, "Error message must not be null."), cause);
        this.errorCode = Objects.requireNonNull(errorCode, "ErrorCode must not be null.");
    }

    /**
     * 指定されたエラーコードと原因となる例外を使用して、新しいApplicationExceptionを構築します。
     * メッセージはErrorCodeのデフォルトメッセージを使用します。
     *
     * @param errorCode この例外に関連付けられるエラーコード
     * @param cause この例外の原因となる例外 (nullが許容される)
     * @throws NullPointerException errorCodeがnullの場合
     */
    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null.").getMessage(), cause);
        this.errorCode = errorCode;
    }

    /**
     * この例外に関連付けられたエラーコードを取得します。
     *
     * @return この例外のエラーコード
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
