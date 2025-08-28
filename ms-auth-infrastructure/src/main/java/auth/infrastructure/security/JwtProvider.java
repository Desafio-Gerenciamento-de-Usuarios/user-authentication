package auth.infrastructure.security;

import auth.domain.exception.InvalidTokenException;
import auth.domain.exception.JwtValidationException;
import auth.domain.model.Token;
import auth.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    @Autowired
    public JwtProvider(
            @Value("${jwt.private-key-path}") String privateKeyPath,
            @Value("${jwt.public-key-path}") String publicKeyPath
    ) throws Exception {
        this.privateKey = loadPrivateKeyFromFile(privateKeyPath);
        this.publicKey = loadPublicKeyFromFile(publicKeyPath);
    }

    private RSAPrivateKey loadPrivateKeyFromFile(String filePath) throws Exception {
        String keyContent = Files.readString(Paths.get(filePath));
        byte[] keyBytes = stripPemHeaderFooter(keyContent);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    private RSAPublicKey loadPublicKeyFromFile(String filePath) throws Exception {
        String keyContent = Files.readString(Paths.get(filePath));
        byte[] keyBytes = stripPemHeaderFooter(keyContent);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public JwtProvider(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Token createToken(User user) {
        final Instant expiresAt = Instant.now().plus(1, ChronoUnit.HOURS);
        final String jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("type", "refresh")
                .withExpiresAt(Date.from(expiresAt))
                .sign(Algorithm.RSA256(null, privateKey));

        return new Token(jwt, expiresAt);
    }

    public Token refreshToken(String refreshToken) {

        try {
            final DecodedJWT decodedJWT = verifyToken(refreshToken);
            final String tokenType = decodedJWT.getClaim("type").asString();
            if (!"refresh".equals(tokenType)) {
                throw new JWTVerificationException("Token não é um refresh token válido");
            }
            final String userId = decodedJWT.getSubject();
            final String username = decodedJWT.getClaim("username").asString();
            final User user = new User(UUID.fromString(userId), username);
            return createToken(user);
        } catch (InvalidTokenException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidTokenException("Erro ao processar refresh token", e);
        }
    }

    private DecodedJWT verifyToken(String token) {
        try {
            final Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            final JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTDecodeException e) {
            throw new InvalidTokenException("Formato do token inválido: " + e.getMessage(), e);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            throw new TokenExpiredException("Token expirado", e.getExpiredOn());
        } catch (JWTVerificationException e) {
            throw new InvalidTokenException("Token JWT inválido: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new InvalidTokenException("Erro ao verificar token", e);
        }
    }

    private byte[] stripPemHeaderFooter(String keyContent) {
        String clean = keyContent
                .replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(clean);
    }
}
