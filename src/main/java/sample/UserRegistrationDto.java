package sample;

import java.util.Objects;

/**
 * ユーザー登録用のデータモデル (DTO: Data Transfer Object)。
 * ユーザー名、パスワード、メールアドレスなどの情報保持します。
 *
 * <p>フィールドの明示的な指定がないため、一般的なユーザー登録で想定される
 * フィールド（username, password, email）を仮定して実装しています。</p>
 */
public class UserRegistrationDto {

    private String username;
    private String password;
    private String email;

    /**
     * デフォルトコンストラクタ。
     * 各フィールドはnullで初期化されます。
     */
    public UserRegistrationDto() {
    }

    /**
     * 全てのフィールドを初期化するコンストラクタ。
     *
     * @param username ユーザー名
     * @param password パスワード
     * @param email メールアドレス
     */
    public UserRegistrationDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * ユーザー名を取得します。
     *
     * @return ユーザー名
     */
    public String getUsername() {
        return username;
    }

    /**
     * ユーザー名を設定します。
     *
     * @param username 設定するユーザー名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * パスワードを取得します。
     *
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定します。
     *
     * @param password 設定するパスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * メールアドレスを取得します。
     *
     * @return メールアドレス
     */
    public String getEmail() {
        return email;
    }

    /**
     * メールアドレスを設定します。
     *
     * @param email 設定するメールアドレス
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * このオブジェクトが指定されたオブジェクトと等しいかどうかを判断します。
     * username, password, email のフィールドが全て等しい場合にtrueを返します。
     *
     * @param o 比較するオブジェクト
     * @return 指定されたオブジェクトがこのオブジェクトと等しい場合はtrue、それ以外の場合はfalse
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegistrationDto that = (UserRegistrationDto) o;
        return Objects.equals(username, that.username) &&
               Objects.equals(password, that.password) &&
               Objects.equals(email, that.email);
    }

    /**
     * このオブジェクトのハッシュコード値を返します。
     * username, password, email のフィールドに基づいてハッシュ値を生成します。
     *
     * @return このオブジェクトのハッシュコード値
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }

    /**
     * このオブジェクトの文字列表現を返します。
     * セキュリティのため、パスワードは "[PROTECTED]" と表示されます。
     *
     * @return このオブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return "UserRegistrationDto{" +
               "username='" + username + '\'' +
               ", password='[PROTECTED]'" + // パスワードはログに出力しないのが一般的
               ", email='" + email + '\'' +
               '}';
    }
}
