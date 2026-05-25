package com.example.userregistration.common.exception;

import java.util.Objects;

/**
 * リポジトリ層でのデータベース操作中に発生するシステムエラーを示す例外です。
 * 主に{@link ErrorCode#DATABASE_OPERATION_ERROR}と関連付けられます。
 */
public class RepositoryException extends ApplicationException {

    /**
     * 指定されたエラーコードと原因となる例外を使用して、新しいRepositoryExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられるエラーコード。通常は{@link ErrorCode#DATABASE_OPERATION_ERROR}。
     * @param cause この例外の原因となる例外。
     * @throws NullPointerException errorCodeまたはcauseがnullの場合
     */
    public RepositoryException(ErrorCode errorCode, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null."),
              Objects.requireNonNull(errorCode.getMessage(), "Error message must not be null."),
              Objects.requireNonNull(cause, "Cause must not be null."));
        // システムエラーであることを確認
        if (errorCode.getType() != ErrorCode.ErrorType.SYSTEM) {
            // 例外の設計意図に反する場合、警告やより具体的なエラーコードへの変換を検討
            // ここではシンプルにそのまま受け入れる
        }
    }

    /**
     * 指定されたエラーコード、メッセージ、および原因となる例外を使用して、新しいRepositoryExceptionを構築します。
     *
     * @param errorCode この例外に関連付けられるエラーコード。通常は{@link ErrorCode#DATABASE_OPERATION_ERROR}。
     * @param message 例外の詳細メッセージ。
     * @param cause この例外の原因となる例外。
     * @throws NullPointerException errorCodeまたはcauseがnullの場合
     */
    public RepositoryException(ErrorCode errorCode, String message, Throwable cause) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null."),
              Objects.requireNonNull(message, "Error message must not be null."),
              Objects.requireNonNull(cause, "Cause must not be null."));
        if (errorCode.getType() != ErrorCode.ErrorType.SYSTEM) {
            // 例外の設計意図に反する場合、警告やより具体的なエラーコードへの変換を検討
        }
    }
}
