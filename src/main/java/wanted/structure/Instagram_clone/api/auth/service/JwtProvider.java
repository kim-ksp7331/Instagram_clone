package wanted.structure.Instagram_clone.api.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wanted.structure.Instagram_clone.api.user.entity.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;


    @Value("${jwt.secret}")
    private String JWT_KEY;


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey(JWT_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String doGenerateToken(String email, long expire) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        long now = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + expire))
            .signWith(getSecretKey(JWT_KEY), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(User user) {
        return doGenerateToken(user.getEmail(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    private String getUserEmail(String jwtToken) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey(JWT_KEY))
            .build()
            .parseClaimsJws(jwtToken)
            .getBody()
            .get("email", String.class);
    }


    private Key getSecretKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
