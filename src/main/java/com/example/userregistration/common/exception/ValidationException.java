package com.example.userregistration.common.exception;

import java.util.Objects;

/**
 * バリデーションエラーなどの業務ルール違反を示す例外です。
 * {@link ErrorCode#VALIDATION_ERROR} またはその他の業務エラーコードと関連付けられます。
 */
public class ValidationException extends ApplicationException {

    /**
     * 指定されたエラーコードとメッセージを使用して、新しいValidationExceptionを構築します。
     * 主に{@link ErrorCode#VALIDATION_ERROR}または他の業務エラーコードが使用されます。
     *
     * @param errorCode この例外に関連付けられるエラーコード
     * @param message 例外の詳細メッセージ
     * @throws NullPointerException errorCodeがnullの場合
     */
    public ValidationException(ErrorCode errorCode, String message) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null."),
              Objects.requireNonNull(message, "Error message must not be null."));
        // 業務エラーであることを確認 (厳密には必要ないが、意図を明確にするため)
        if (errorCode.getType() != ErrorCode.ErrorType.BUSINESS) {
            // 例外の設計意図に反する場合、警告やより具体的なエラーコードへの変換を検討
            // ここではシンプルにそのまま受け入れる
        }
    }

    /**
     * 指定されたエラーコードを使用して、新しいValidationExceptionを構築します。
     * メッセージはErrorCodeのデフォルトメッセージを使用します。
     *
     * @param errorCode この例外に関連付けられるエラーコード
     * @throws NullPointerException errorCodeがnullの場合
     */
    public ValidationException(ErrorCode errorCode) {
        super(Objects.requireNonNull(errorCode, "ErrorCode must not be null."),
              errorCode.getMessage());
        if (errorCode.getType() != ErrorCode.ErrorType.BUSINESS) {
            // 例外の設計意図に反する場合、警告やより具体的なエラーコードへの変換を検討
        }
    }
}
