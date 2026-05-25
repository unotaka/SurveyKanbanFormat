package com.example.userregistration.common.exception;

import java.util.Collections;
import java.util.List;

/**
 * 業務処理において発生するエラー（例：バリデーション違反、データの重複など）を表す例外クラス。
 * この例外は、ユーザーへのフィードバックを目的としたエラーメッセージのリストを持つことができます。
 */
public class BusinessException extends ApplicationException {

    private final List<String> errorMessages;

    /**
     * 指定された詳細メッセージを持つ新しい業務例外を構築します。
     *
     * @param message 詳細メッセージ
     */
    public BusinessException(String message) {
        super(message);
        this.errorMessages = Collections.singletonList(message);
    }

    /**
     * 指定された詳細メッセージと原因を持つ新しい業務例外を構築します。
     *
     * @param message 詳細メッセージ
     * @param cause   原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessages = Collections.singletonList(message);
    }

    /**
     * 複数のエラーメッセージを持つ新しい業務例外を構築します。
     *
     * @param errorMessages エラーメッセージのリスト
     */
    public BusinessException(List<String> errorMessages) {
        super(errorMessages != null && !errorMessages.isEmpty() ? String.join(", ", errorMessages) : "業務処理中にエラーが発生しました。");
        this.errorMessages = Collections.unmodifiableList(errorMessages != null ? errorMessages : Collections.emptyList());
    }

    /**
     * この例外に関連付けられたエラーメッセージのリストを返します。
     *
     * @return エラーメッセージの不変リスト
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
