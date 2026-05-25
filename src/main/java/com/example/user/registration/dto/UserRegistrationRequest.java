package com.example.user.registration.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * ユーザー登録リクエストのデータモデルを表すレコードです。
 * 新しいユーザーを登録するために必要な情報を保持します。
 */
public record UserRegistrationRequest(
        /**
         * ユーザーID。システム内で一意である必要があります。
         * 必須フィールドです。
         */
        String userId,
        /**
         * ユーザーのパスワード。セキュリティ要件に応じてハッシュ化などの処理が必要です。
         * 必須フィールドです。
         */
        String password,
        /**
         * ユーザーのメールアドレス。
         * 必須フィールドです。
         */
        String email,
        /**
         * ユーザーの名。
         * 必須フィールドです。
         */
        String firstName,
        /**
         * ユーザーの姓。
         * 必須フィールドです。
         */
        String lastName,
        /**
         * ユーザーの生年月日。
         * 必須フィールドです。
         */
        LocalDate dateOfBirth,
        /**
         * ユーザーの電話番号。
         * 任意フィールドです。
         */
        String phoneNumber
) {
    /**
     * UserRegistrationRequestのコンパクトコンストラクタ。
     * 必須フィールドがnullでないことを検証します。
     *
     * @param userId      ユーザーID
     * @param password    パスワード
     * @param email       メールアドレス
     * @param firstName   名
     * @param lastName    姓
     * @param dateOfBirth 生年月日
     * @param phoneNumber 電話番号
     * @throws NullPointerException 必須フィールドがnullの場合
     */
    public UserRegistrationRequest {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(password, "password must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(dateOfBirth, "dateOfBirth must not be null");
        // phoneNumberは任意のため、nullチェックは行わない
    }
}
