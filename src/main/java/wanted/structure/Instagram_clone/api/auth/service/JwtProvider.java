package wanted.structure.Instagram_clone.api.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wanted.structure.Instagram_clone.global.exception.ForbiddenException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String JWT_KEY;

    private final PrincipalDetailsService principalDetailsService;


    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey(JWT_KEY))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String createToken(String email, long expire) {
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

    public boolean validateToken(String token) {
        try {
            log.info("jwt validate...");
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey(JWT_KEY))
                .build()
                .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            throw new ForbiddenException();
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(getUserEmail(jwtToken));
//        log.info("PASSWORD : {}",userDetails.getPassword()); // 패스워드 왜 보여줘 미친놈아
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
            userDetails.getAuthorities());
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