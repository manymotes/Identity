package com.example.identity.user.passwordService;

import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.inject.Named;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Named
public class JwtService {
    private final ECPublicKey publicKey = loadPublicKey("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE3dFS5Wi9981LPmCYEmxFylrHWQ8P/PQEs/ADWAdxHFH+NS4su8pl8fsJnPco8Trn8B+33fDms3VSj86e+rArsZFGrO6kWoVm31WmX1/aNbZnzG/RE76m87o86VzZfyMU");
    ;
    private final ECPrivateKey privateKey = loadPrivateKey("MIG2AgEAMBAGByqGSM49AgEGBSuBBAAiBIGeMIGbAgEBBDC7F6pKTsP68LL7xPHtfY6yDgNGd/QfrVVwaDOylzp44ikMuPS3yZBhlbJaFgw8hgehZANiAATd0VLlaL33zUs+YJgSbEXKWsdZDw/89ASz8ANYB3EcUf41Liy7ymXx+wmc9yjxOufwH7fd8OazdVKPzp76sCuxkUas7qRahWbfVaZfX9o1tmfMb9ETvqbzujzpXNl/IxQ=");
    private final JWTVerifier jwtVerifier = JWT.require(algorithm()).build();


    //todo make private and public key come from a property yaml file
//    @Inject
//    public JwtService(@Value("${auth.signingKey.public}") String publicKey, @Value("${auth.signingKey.private}") String privateKey) {
//        this.publicKey = loadPublicKey(publicKey);
//        this.privateKey = loadPrivateKey(privateKey);
//        this.jwtVerifier = JWT.require(algorithm()).build();
//    }

    public String buildToken(UUID sessionUuid, UUID userUuid, Instant created, Instant expires) {
        return JWT.create()
            .withJWTId(sessionUuid.toString())
            .withSubject(userUuid.toString())
            .withExpiresAt(new Date(expires.toEpochMilli()))
            .withIssuedAt(new Date(created.toEpochMilli()))
            .withNotBefore(new Date(created.toEpochMilli()))
            .sign(algorithm());
    }

    public DecodedJWT decodeToken(String jwtToken) {
        try {
            return JWT.decode(jwtToken);
        } catch (Exception e) {
            return null;
        }
    }

    public DecodedJWT validateToken(String jwtToken) {
        try {
            return jwtVerifier.verify(jwtToken);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(DecodedJWT jwt) {
        try {
            jwtVerifier.verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Algorithm algorithm() {
        return Algorithm.ECDSA384(publicKey, privateKey);
    }

    private static ECPublicKey loadPublicKey(String publicKey) {
        final byte[] bytes = Base64.getDecoder().decode(publicKey);
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return (ECPublicKey) keyFactory.generatePublic(keySpec);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ECPrivateKey loadPrivateKey(String privateKey) {
        final byte[] bytes = Base64.getDecoder().decode(privateKey);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return (ECPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
