package com.example.userregistration.common.exception;

/**
 * ユーザー登録処理中に発生したシステムエラーを示す例外です。
 * 例として、データベース接続エラー、予期せぬデータ処理エラーなどが挙げられます。
 */
public class UserRegistrationException extends ApplicationException {

    /**
     * 指定されたエラーコードと詳細メッセージを使用して、新しいUserRegistrationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられたエラーコード。
     * @param message   この例外の詳細メッセージ。
     */
    public UserRegistrationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 指定されたエラーコード、詳細メッセージ、および原因となった例外を使用して、
     * 新しいUserRegistrationExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられたエラーコード。
     * @param message   この例外の詳細メッセージ。
     * @param cause     この例外の根本原因。
     */
    public UserRegistrationException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
