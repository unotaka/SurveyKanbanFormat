package com.example.userregistration.common.exception;

/**
 * アプリケーション内で発生するエラーコードを定義する列挙型です。
 * 業務エラーとシステムエラーを区別し、詳細なエラー情報を提供します。
 */
public enum ErrorCode {

    // ----------------------------------------------------
    // 業務エラー (Business Errors)
    // ----------------------------------------------------

    /**
     * 入力値が不正です。バリデーションルールに違反しています。
     */
    VALIDATION_ERROR("B001", "入力値が不正です。", ErrorType.BUSINESS),

    /**
     * ユーザー名が既に登録されています。
     */
    USERNAME_ALREADY_EXISTS("B002", "指定されたユーザー名は既に登録されています。", ErrorType.BUSINESS),

    /**
     * メールアドレスが既に登録されています。
     */
    EMAIL_ALREADY_EXISTS("B003", "指定されたメールアドレスは既に登録されています。", ErrorType.BUSINESS),

    /**
     * パスワードポリシーに違反しています。
     */
    PASSWORD_POLICY_VIOLATION("B004", "パスワードがポリシー要件を満たしていません。", ErrorType.BUSINESS),

    // ----------------------------------------------------
    // システムエラー (System Errors)
    // ----------------------------------------------------

    /**
     * データベース操作中に予期せぬエラーが発生しました。
     */
    DATABASE_OPERATION_ERROR("S001", "データベース操作中に予期せぬエラーが発生しました。", ErrorType.SYSTEM),

    /**
     * パスワードの暗号化処理中にエラーが発生しました。
     */
    PASSWORD_ENCRYPTION_ERROR("S002", "パスワードの暗号化処理中にエラーが発生しました。", ErrorType.SYSTEM),

    /**
     * 予期せぬシステムエラーが発生しました。
     */
    UNEXPECTED_SYSTEM_ERROR("S999", "予期せぬシステムエラーが発生しました。", ErrorType.SYSTEM);

    /**
     * エラーコードの識別子です（例: B001, S001）。
     */
    private final String code;

    /**
     * エラーメッセージのテンプレートです。
     */
    private final String message;

    /**
     * エラーの種別（業務エラー、システムエラー）です。
     */
    private final ErrorType type;

    /**
     * ErrorCodeのコンストラクタ。
     *
     * @param code エラーコードの識別子
     * @param message エラーメッセージのテンプレート
     * @param type エラーの種別
     */
    ErrorCode(String code, String message, ErrorType type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    /**
     * エラーコードの識別子を取得します。
     *
     * @return エラーコードの識別子
     */
    public String getCode() {
        return code;
    }

    /**
     * エラーメッセージのテンプレートを取得します。
     *
     * @return エラーメッセージのテンプレート
     */
    public String getMessage() {
        return message;
    }

    /**
     * エラーの種別を取得します。
     *
     * @return エラーの種別
     */
    public ErrorType getType() {
        return type;
    }

    /**
     * エラーの種別を定義する列挙型です。
     */
    public enum ErrorType {
        /** 業務ロジックに起因するエラー */
        BUSINESS,
        /** システムの内部的な問題に起因するエラー */
        SYSTEM
    }
}
