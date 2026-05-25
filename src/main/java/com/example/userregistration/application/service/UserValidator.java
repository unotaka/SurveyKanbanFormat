package com.example.userregistration.application.service;

import com.example.userregistration.presentation.dto.UserRegisterRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ユーザー登録リクエストのバリデーションロジックを提供します。
 * 各入力フィールドに対する具体的なルールチェックを行います。
 */
public class UserValidator {

    // ユーザー名の正規表現: 英数字のみ、3文字以上20文字以下
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,20}$");
    // メールアドレスの正規表現: 標準的な形式
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    // パスワードの正規表現: 8文字以上30文字以下、少なくとも1つの数字、1つの小文字、1つの大文字、1つの特殊文字
    // [!@#$%^&*()_+\-=\[\]{};':"\\|,.<>/?~]
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~]{8,30}$");

    /**
     * ユーザー登録リクエストDTOの入力値をバリデーションします。
     * 複数のバリデーションエラーがある場合、それらをリストにまとめて返します。
     *
     * @param request バリデーション対象のUserRegisterRequest DTO
     * @return バリデーションエラーメッセージのリスト。エラーがない場合は空のリスト
     */
    public List<String> validate(UserRegisterRequest request) {
        List<String> errors = new ArrayList<>();

        // usernameのバリデーション
        if (request.username() == null || request.username().isBlank()) {
            errors.add("ユーザー名は必須です。");
        } else if (!USERNAME_PATTERN.matcher(request.username()).matches()) {
            errors.add("ユーザー名は英数字で3文字以上20文字以下である必要があります。");
        }

        // passwordのバリデーション
        if (request.password() == null || request.password().isBlank()) {
            errors.add("パスワードは必須です。");
        } else if (!PASSWORD_PATTERN.matcher(request.password()).matches()) {
            errors.add("パスワードは8文字以上30文字以下で、半角英大文字、半角英小文字、数字、特殊文字をそれぞれ1種類以上含める必要があります。");
        }

        // emailのバリデーション
        if (request.email() == null || request.email().isBlank()) {
            errors.add("メールアドレスは必須です。");
        } else if (!EMAIL_PATTERN.matcher(request.email()).matches()) {
            errors.add("メールアドレスの形式が正しくありません。");
        }

        // displayNameのバリデーション
        if (request.displayName() == null || request.displayName().isBlank()) {
            errors.add("表示名は必須です。");
        } else if (request.displayName().length() < 1 || request.displayName().length() > 50) {
            errors.add("表示名は1文字以上50文字以下である必要があります。");
        }

        return errors;
    }
}
