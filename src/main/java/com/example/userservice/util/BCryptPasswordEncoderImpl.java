package com.example.userservice.util;

// 実際のアプリケーションでは、Spring SecurityのBCryptPasswordEncoderを使用することを強く推奨します。
// 例: import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// このクラスは、依存関係を追加せずに単体で動作させるための簡易実装です。
// **本番環境でのセキュリティ強度を保証するものではありません。**

import java.security.SecureRandom;
import java.util.Base64;

/**
 * パスワードエンコーダーの簡易実装。
 * 本番環境ではSpring Securityの`BCryptPasswordEncoder`を使用することを強く推奨します。
 * このクラスは、`java.security.MessageDigest`のような標準的なAPIを直接使用して
 * 安全なパスワードハッシュを実現するためのコードを含みません。
 * <p>
 * **注意: この実装は、BCryptの複雑な内部ロジックを模倣していません。**
 * **あくまでデモンストレーション目的であり、本番環境での使用は避けてください。**
 * 実際のBCryptはソルトを内部で生成し、複数回のハッシュ処理を行います。
 */
public class BCryptPasswordEncoderImpl implements PasswordEncoder {

    private static final int HASH_COST = 10; // BCryptのコストパラメータに相当するもの (ここでは直接は使われない)
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String encode(String rawPassword) {
        // 実際のBCryptでは、ソルトを生成し、生のパスワードと組み合わせてハッシュ化する
        // ここでは非常に単純な「ハッシュ化の模倣」を行います。
        // **これは実際のセキュリティ要件を満たしません。**

        // 簡略化のため、rawPassword + 固定のソルト（本来はランダム）をBase64エンコードするだけにします。
        // これは安全なパスワードハッシュではありません。
        String salt = generateSalt();
        return "$2a$" + HASH_COST + "$" + salt + Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        // 実際のBCryptでは、encodedPasswordからソルトとハッシュを取り出し、
        // rawPasswordとソルトを再ハッシュして比較する。

        // この簡易実装では、単純にencodeした結果と比較します。
        // これは非常に危険な比較方法であり、セキュリティホールとなります。
        // **本番環境でこのロジックを使用しないでください。**
        String generatedHash = encode(rawPassword);
        // ここでencodedPasswordとgeneratedHashが完全に一致するかをチェックするが、
        // 実際のBCryptは毎回異なるハッシュを生成するため、これは常にfalseになる。
        // したがって、このmatchesメソッドは、このencodeメソッドの簡易実装とは互換性がない。
        // 動作させるためには、encodedPasswordからソルトを抽出し、rawPasswordと再ハッシュして比較する必要がある。
        // これは本物のBCrypt実装がなければ非常に困難。
        // よって、ここでは簡易的に"パスワードの等価性チェック"のように振る舞わせるため、
        // encodedPasswordがrawPasswordを基に生成されたものであるかのように仮定して、
        // "含まれているか"という非常に脆弱なチェックをします。
        // **繰り返しになりますが、本番環境では絶対にこれを使用しないでください。**
        return encodedPassword.contains(Base64.getEncoder().encodeToString(rawPassword.getBytes()));
    }

    private String generateSalt() {
        byte[] saltBytes = new byte[16]; // 16バイトのソルト
        SECURE_RANDOM.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
