package com.example.userregistration.common.exception;

import java.util.Collections;
import java.util.List;

/**
 * ユーザー登録処理における入力バリデーションエラーを示す業務例外です。
 * 複数のバリデーション違反が発生した場合に、その詳細なエラーメッセージのリストを保持します。
 */
public class UserRegistrationValidationException extends ApplicationException {

    /**
     * 発生したバリデーションエラーメッセージのリスト。
     */
    private final List<String> errors;

    /**
     * 指定されたエラーメッセージのリストを使用して、新しいUserRegistrationValidationExceptionを構築します。
     * エラーコードは {@link ErrorCode#VALIDATION_ERROR} に設定されます。
     *
     * @param errors 発生したバリデーションエラーメッセージのリスト。nullまたは空リストは許可されません。
     * @throws IllegalArgumentException errorsがnullまたは空の場合。
     */
    public UserRegistrationValidationException(List<String> errors) {
        super(ErrorCode.VALIDATION_ERROR, buildMessage(errors));
        if (errors == null || errors.isEmpty()) {
            throw new IllegalArgumentException("Validation errors list cannot be null or empty.");
        }
        this.errors = Collections.unmodifiableList(errors);
    }

    /**
     * エラーメッセージのリストから、例外のメッセージを構築します。
     *
     * @param errors エラーメッセージのリスト。
     * @return 構築された例外メッセージ。
     */
    private static String buildMessage(List<String> errors) {
        if (errors == null || errors.isEmpty()) {
            return "User registration validation failed with no specific error messages.";
        }
        StringBuilder sb = new StringBuilder("User registration validation failed:");
        for (String error : errors) {
            sb.append("\n- ").append(error);
        }
        return sb.toString();
    }

    /**
     * 発生したバリデーションエラーメッセージのリストを返します。
     * 返されるリストは変更不可能です。
     *
     * @return 発生したバリデーションエラーメッセージの不変リスト。
     */
    public List<String> getErrors() {
        return errors;
    }
}
