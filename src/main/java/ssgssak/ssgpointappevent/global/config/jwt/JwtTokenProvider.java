package ssgssak.ssgpointappevent.global.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ssgssak.ssgpointappevent.global.common.exception.TokenExpiredException;
import ssgssak.ssgpointappevent.global.common.exception.TokenInvalidException;

import java.security.Key;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final Environment env; // application.yml에서 설정한 설정값
    @Value("${jwt.secret-key}")
    private String secretKey;

    /**
     * TokenProvider
     * 1. 토큰에서 uuid 가져오기
     * 2. Claims에서 원하는 claim 값 추출
     * 3. 토큰에서 모든 claims 추출
     * 4. 토큰 key 디코드
     */

    // 1. 토큰에서 uuid 가져오기 가져오기
    public String getUUID(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 2. Claims에서 원하는 값(T) 추출 : (token)과, (Claims를 받아서 원하는 값 T를 반환하는 function)을 입력받아서 -> T를 return
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 3. 토큰에서 모든 claim 추출 : token을 파싱해서 모든 claim 값을 추출한다
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token) // 이 단계에서 token의 유효성 검사 및 만료일 검사를 실시한다!
                    .getBody();
        }
        // parseClaimsJws에서 토큰이 잘못된 경우 error를 발생시킨다! -> 발생한 오류는 ExceptionHandlerFilter로 가서 처리된다!
        catch (ExpiredJwtException e) {
            log.error("토큰오류 -> " + e);
            throw new TokenExpiredException("토큰 파싱단계 - 토큰시간 만료");
        } catch (Exception e) {
            throw new TokenInvalidException("토큰 파싱단계 - 유효하지않은 토큰");
        }
    }

    // 4. 토큰 key 디코드 : env에 저장된 키로, 들어온 토큰을 파싱
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); //(env.getProperty("JWT.SECRET_KEY", String.class));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
