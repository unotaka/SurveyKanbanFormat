package com.example.userregistration.common.exception;

/**
 * アプリケーション内で発生するエラーコードを定義する列挙型です。
 * 業務エラーとシステムエラーを区別し、特定のエラー状況を識別するために使用されます。
 */
public enum ErrorCode {
    /**
     * 入力バリデーションエラーが発生した場合のエラーコード。
     * 通常、複数のバリデーション違反がある場合に使用されます。
     */
    VALIDATION_ERROR,

    /**
     * ユーザー名が既に登録されている場合のエラーコード。
     */
    DUPLICATE_USERNAME,

    /**
     * メールアドレスが既に登録されている場合のエラーコード。
     */
    DUPLICATE_EMAIL,

    /**
     * データベース操作中に予期しないエラーが発生した場合のエラーコード。
     * 例: 接続エラー、データの整合性違反（VALIDATION_ERROR以外のもの）など。
     */
    DATABASE_ERROR,

    /**
     * その他の予期しないシステムエラーが発生した場合のエラーコード。
     * 特定の分類に当てはまらない、一般的なシステム障害を示す。
     */
    UNKNOWN_SYSTEM_ERROR
}
