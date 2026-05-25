package com.example.userregistration.user.service.impl;

import com.example.userregistration.common.exception.ErrorCode;
import com.example.userregistration.common.exception.RepositoryException;
import com.example.userregistration.user.service.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt; // BCryptライブラリを想定
import org.springframework.stereotype.Component; // Springの@Componentアノテーションを想定

import java.util.Objects;

/**
 * {@link PasswordEncoder} のBCrypt実装です。
 * <p>
 * このクラスは{@code org.mindrot.jbcrypt.BCrypt}ライブラリに依存します。
 * プロジェクトのpom.xmlまたはbuild.gradleに以下の依存関係を追加してください:
 * </p>
 * <pre>
 * &lt;dependency&gt;
 *     &lt;groupId&gt;org.mindrot&lt;/groupId&gt;
 *     &lt;artifactId&gt;jbcrypt&lt;/artifactId&gt;
 *     &lt;version&gt;0.4&lt;/version&gt; &lt;!-- または最新バージョン --&gt;
 * &lt;/dependency&gt;
 * </pre>
 */
@Component // Spring Frameworkのコンポーネントとして認識されるようにアノテーションを付与
@Slf4j // Lombokによるロガー
public class BCryptPasswordEncoder implements PasswordEncoder {

    // BCryptのストレングスコスト (4-31). コストが高いほどハッシュ化に時間がかかり、攻撃に強くなります。
    // 開発/テスト環境では低めに、本番環境では高めに設定することを推奨します。
    private static final int HASH_STRENGTH = 10;

    /**
     * 生のパスワードをBCryptアルゴリズムを使用してハッシュ化します。
     *
     * @param rawPassword ハッシュ化する生のパスワード
     * @return ハッシュ化されたパスワード文字列
     * @throws RepositoryException パスワードのハッシュ化中にシステムエラーが発生した場合
     * @throws NullPointerException rawPasswordがnullの場合
     */
    @Override
    public String encode(String rawPassword) throws RepositoryException {
        requireNonNull(rawPassword, "rawPassword");
        try {
            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(HASH_STRENGTH));
            log.debug("Password encoded successfully.");
            return hashedPassword;
        } catch (IllegalArgumentException e) {
            log.error("Failed to encode password due to invalid argument. RawPassword length or BCrypt salt round issue.", e);
            throw new RepositoryException(ErrorCode.PASSWORD_ENCRYPTION_ERROR, "パスワードのハッシュ化に失敗しました。", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred during password encoding.", e);
            throw new RepositoryException(ErrorCode.PASSWORD_ENCRYPTION_ERROR, "パスワードのハッシュ化中に予期せぬエラーが発生しました。", e);
        }
    }

    /**
     * ハッシュ化されたパスワードと生のパスワードがBCryptアルゴリズムに基づいて一致するかどうかを検証します。
     *
     * @param rawPassword 検証する生のパスワード
     * @param encodedPassword 比較対象のハッシュ化されたパスワード
     * @return パスワードが一致すればtrue、そうでなければfalse
     * @throws RepositoryException パスワードの検証中にシステムエラーが発生した場合
     * @throws NullPointerException rawPasswordまたはencodedPasswordがnullの場合
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) throws RepositoryException {
        requireNonNull(rawPassword, "rawPassword");
        requireNonNull(encodedPassword, "encodedPassword");
        try {
            boolean isMatch = BCrypt.checkpw(rawPassword, encodedPassword);
            log.debug("Password match check result: {}", isMatch);
            return isMatch;
        } catch (IllegalArgumentException e) {
            log.error("Failed to check password match due to invalid argument. Likely malformed encoded password.", e);
            throw new RepositoryException(ErrorCode.PASSWORD_ENCRYPTION_ERROR, "パスワードの検証に失敗しました。（不正なハッシュ形式）", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred during password matching.", e);
            throw new RepositoryException(ErrorCode.PASSWORD_ENCRYPTION_ERROR, "パスワードの検証中に予期せぬエラーが発生しました。", e);
        }
    }
}
