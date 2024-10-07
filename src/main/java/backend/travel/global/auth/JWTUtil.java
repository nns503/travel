package backend.travel.global.auth;

import backend.travel.domain.user.entity.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.expiration}")
    private long accessExpiration;
    @Value("${spring.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String createRefreshToken(Long userId, String role) {
        return Jwts.builder()
                .claim("category", "refresh")
                .claim("id", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public String createAccessToken(Long userId, String role) {
        return Jwts.builder()
                .claim("category", "access")
                .claim("id", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public Long getUserId(String token){
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }


    public UserRole getRole(String token){
        String roleString = Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return UserRole.valueOf(roleString);
    }

    public boolean isValidToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.info("JWT 서명이 잘못 되었습니다.",  e);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료 되었습니다.",  e);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.",  e);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.",  e);
        } catch (Exception e){
            log.info("알 수 없는 JWT 오류입니다.", e);
        }
        return false;
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
