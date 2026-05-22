package com.example;

import com.example.user.dto.UserRegistrationRequest;
import com.example.user.dto.UserRegistrationResponse;
import com.example.user.exception.EmailAlreadyExistsException;
import com.example.user.exception.InvalidRegistrationDataException;
import com.example.user.exception.UsernameAlreadyExistsException;
import com.example.user.repository.MockUserRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;

/**
 * ユーザー登録処理の動作確認を行うためのメインアプリケーションクラスです。
 * 通常はコントローラー層からUserServiceが呼び出されますが、ここでは直接呼び出して動作を確認します。
 */
public class MainApplication {

    public static void main(String[] args) {
        // 依存関係のセットアップ
        UserRepository userRepository = new MockUserRepository(); // モックリポジトリを使用
        UserService userService = new UserService(userRepository);

        System.out.println("--- ユーザー登録処理テスト開始 ---");

        // 1. 正常なユーザー登録
        System.out.println("\n=== 正常な登録ケース ===");
        UserRegistrationRequest successRequest = new UserRegistrationRequest(
                "testuser", "password123", "test@example.com");
        try {
            UserRegistrationResponse response = userService.registerUser(successRequest);
            System.out.println("登録成功: " + response.getMessage() +
                               ", User ID: " + response.getUserId() +
                               ", Username: " + response.getUsername());
        } catch (Exception e) {
            System.err.println("登録失敗: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 2. ユーザー名が重複するケース
        System.out.println("\n=== ユーザー名重複ケース ===");
        UserRegistrationRequest duplicateUsernameRequest = new UserRegistrationRequest(
                "testuser", "newpassword456", "another@example.com");
        try {
            userService.registerUser(duplicateUsernameRequest);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println("登録失敗 (想定通り): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("登録失敗 (予期せぬエラー): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 3. メールアドレスが重複するケース
        System.out.println("\n=== メールアドレス重複ケース ===");
        UserRegistrationRequest duplicateEmailRequest = new UserRegistrationRequest(
                "anotheruser", "password789", "test@example.com");
        try {
            userService.registerUser(duplicateEmailRequest);
        } catch (EmailAlreadyExistsException e) {
            System.err.println("登録失敗 (想定通り): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("登録失敗 (予期せぬエラー): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 4. ユーザー名が短すぎるケース
        System.out.println("\n=== ユーザー名が短すぎるケース ===");
        UserRegistrationRequest shortUsernameRequest = new UserRegistrationRequest(
                "ab", "password123", "short@example.com");
        try {
            userService.registerUser(shortUsernameRequest);
        } catch (InvalidRegistrationDataException e) {
            System.err.println("登録失敗 (想定通り): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("登録失敗 (予期せぬエラー): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 5. パスワードが短すぎるケース
        System.out.println("\n=== パスワードが短すぎるケース ===");
        UserRegistrationRequest shortPasswordRequest = new UserRegistrationRequest(
                "validuser", "short", "valid@example.com");
        try {
            userService.registerUser(shortPasswordRequest);
        } catch (InvalidRegistrationDataException e) {
            System.err.println("登録失敗 (想定通り): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("登録失敗 (予期せぬエラー): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 6. メールアドレスの形式が不正なケース
        System.out.println("\n=== 不正なメールアドレス形式ケース ===");
        UserRegistrationRequest invalidEmailRequest = new UserRegistrationRequest(
                "validuser2", "password123", "invalid-email");
        try {
            userService.registerUser(invalidEmailRequest);
        } catch (InvalidRegistrationDataException e) {
            System.err.println("登録失敗 (想定通り): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("登録失敗 (予期せぬエラー): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        // 7. 全てのフィールドが有効で、新しいユーザーを登録するケース
        System.out.println("\n=== 全て有効な新規登録ケース ===");
        UserRegistrationRequest anotherSuccessRequest = new UserRegistrationRequest(
                "newuser123", "StrongPass!1", "newuser@domain.com");
        try {
            UserRegistrationResponse response = userService.registerUser(anotherSuccessRequest);
            System.out.println("登録成功: " + response.getMessage() +
                               ", User ID: " + response.getUserId() +
                               ", Username: " + response.getUsername());
        } catch (Exception e) {
            System.err.println("登録失敗: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }

        System.out.println("\n--- ユーザー登録処理テスト終了 ---");
    }
}
